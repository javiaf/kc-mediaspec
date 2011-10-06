package com.kurento.commons.media.format.formatparameters;

/**
 * Principal interface to manage a=fmtp attribute of payloads
 */
public interface FormatParameters {

	/**
	 * Intersect this FormatParameters with another.
	 * 
	 * @param other FormatParameters object which the intersection will do.
	 * @return a FormatParameters object with the result intersection.
	 */
	public FormatParameters intersect(FormatParameters other);

}
