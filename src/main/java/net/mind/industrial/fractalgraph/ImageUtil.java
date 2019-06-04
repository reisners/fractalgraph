package net.mind.industrial.fractalgraph;

import org.w3c.dom.Node;

import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;

public class ImageUtil
{
    static void configureGIFFrame(IIOMetadata meta, String delayTime, int imageIndex, String disposalMethod, int loopCount)
    {
        String metaFormat = meta.getNativeMetadataFormatName();

        if (!"javax_imageio_gif_image_1.0".equals(metaFormat))
        {
            throw new IllegalArgumentException("Unfamiliar gif metadata format: " + metaFormat);
        }

        Node root = meta.getAsTree(metaFormat);

        Node child = root.getFirstChild();
        while (child != null)
        {
            if ("GraphicControlExtension".equals(child.getNodeName()))
                break;
            child = child.getNextSibling();
        }

        IIOMetadataNode gce = (IIOMetadataNode) child;
        gce.setAttribute("userDelay", "FALSE");
        gce.setAttribute("delayTime", delayTime);
        gce.setAttribute("disposalMethod", disposalMethod);

        if (imageIndex == 0)
        {
            IIOMetadataNode aes = new IIOMetadataNode("ApplicationExtensions");
            IIOMetadataNode ae = new IIOMetadataNode("ApplicationExtension");
            ae.setAttribute("applicationID", "NETSCAPE");
            ae.setAttribute("authenticationCode", "2.0");
            byte[] uo = new byte[] { 0x1, (byte) (loopCount & 0xFF), (byte) ((loopCount >> 8) & 0xFF) };
            ae.setUserObject(uo);
            aes.appendChild(ae);
            root.appendChild(aes);
        }

        try
        {
            meta.setFromTree(metaFormat, root);
        }
        catch (IIOInvalidTreeException e)
        {
            throw new Error(e);
        }
    }
}