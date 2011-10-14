package com.kurento.commons.media.format.formatparameters.impl;

import com.kurento.commons.types.Fraction;

public class H263VideoProfile extends VideoProfileBase {

	private PictureSize pictureSize;

	public PictureSize getPictureSize() {
		return pictureSize;
	}

	public H263VideoProfile(int width, int height, Fraction frameRate) {
		this.width = width;
		this.height = height;
		this.frameRate = frameRate;
		this.pictureSize = PictureSize.getPictureSizeFromSize(width, height);
	}

	public H263VideoProfile(PictureSize pictureSize, int mpi) {
		this.width = pictureSize.getWidth();
		this.height = pictureSize.getHeight();
		this.frameRate = new Fraction(30 * 1000, mpi * 1001);
		this.pictureSize = pictureSize;
	}
}