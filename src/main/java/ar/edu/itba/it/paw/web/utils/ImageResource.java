package ar.edu.itba.it.paw.web.utils;

import java.awt.image.BufferedImage;

import org.apache.wicket.request.resource.DynamicImageResource;

public class ImageResource extends DynamicImageResource {

	private byte[] image;

	public ImageResource(byte[] image) {
		this.image = image;
		setFormat("jpg");
	}

	public ImageResource(BufferedImage image) {
		this.image = toImageData(image);
	}

	@Override
	protected byte[] getImageData(Attributes attributes) {
		if (image != null) {
			return image;
		} else {
			return new byte[0];
		}
	}

}
