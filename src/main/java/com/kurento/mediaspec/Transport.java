/*
 * kc-mediaspec: Media description common format for java
 * Copyright (C) 2012 Tikal Technologies
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.kurento.mediaspec;

import java.io.Serializable;


/**
 * 
 * This class provides a container to specific transport types. In a standard
 * java coding schema this class would have been declared abstract and specific
 * transport classes would ha inherit from it, but composition is used instead
 * in order to facilitate serialization.
 * 
 * @see MediaSpec
 * @see TransportRtmp
 * @see TransportRtp
 */
public class Transport implements Serializable {

	private static final long serialVersionUID = 1L;

	private TransportRtp rtp = null;
	private TransportRtmp rtmp = null;

	/**
	 * Creates an empty transport container
	 */
	public Transport() {

	}

	/**
	 * Create a new transport container as a clone of the given one.
	 * 
	 * @param transport
	 *            Transport to be cloned.
	 */
	public Transport(Transport transport) {
		try {
			this.rtp = new TransportRtp(transport.getRtp());
		} catch (ArgumentNotSetException e) {

		}
		try {
			this.rtmp = new TransportRtmp(transport.getRtmp());
		} catch (ArgumentNotSetException e) {

		}
	}

	/**
	 * Set an RTP transport instance. If previously assigned, old RTP transport
	 * descriptor will be replaced with new one.
	 * 
	 * @param rtp
	 *            RTP transport to be assigned to this container.
	 */
	public synchronized void setRtp(TransportRtp rtp) {
		this.rtp = rtp;
	}

	/**
	 * Set an RTMP transport instance. If previously assigned, old RTMP
	 * transport descriptor will be replaced with new one.
	 * 
	 * @param rtmp
	 *            RTMP transport to be assigned to this container.
	 */
	public synchronized void setRtmp(TransportRtmp rtmp) {
		this.rtmp = rtmp;
	}

	/**
	 * Return RTP transport stored by this container.
	 * 
	 * @return RTP transport stored by this container.
	 * @throws ArgumentNotSetException
	 *             If argument hasn't been initialized.
	 */
	public synchronized TransportRtp getRtp() throws ArgumentNotSetException {
		if (rtp == null)
			throw new ArgumentNotSetException("Rtp is not set");
		return rtp;
	}

	/**
	 * Return RTMP transport store by this container.
	 * 
	 * @return RTMP transport stored by this container.
	 * @throws ArgumentNotSetException
	 *             If argument hasn't been initialized.
	 */
	public synchronized TransportRtmp getRtmp() throws ArgumentNotSetException {
		if (rtmp == null)
			throw new ArgumentNotSetException("Rtmp is not set");
		return rtmp;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Transport [");
		if (rtp != null) {
			builder.append("rtp=");
			builder.append(rtp);
			builder.append(", ");
		}
		if (rtmp != null) {
			builder.append("rtmp=");
			builder.append(rtmp);
		}
		builder.append("]");
		return builder.toString();
	}

	protected static Transport[] intersect(Transport answerer, Transport offerer) {
		Transport neg_answ = new Transport(answerer);
		Transport neg_off = new Transport(offerer);

		try {
			TransportRtmp answ_rtmp = neg_answ.getRtmp();
			TransportRtmp off_rtmp = neg_off.getRtmp();

			TransportRtmp[] rtmps = TransportRtmp.instersect(answ_rtmp,
					off_rtmp);

			if (rtmps == null)
				throw new ArgumentNotSetException();

			neg_answ.rtmp = rtmps[0];
			neg_off.rtmp = rtmps[1];

		} catch (ArgumentNotSetException e) {
			neg_answ.rtmp = null;
			neg_off.rtmp = null;
		}

		if (neg_answ.rtmp == null && neg_answ.rtp == null
				|| neg_off.rtmp == null && neg_off.rtp == null)
			return null;

		return new Transport[] { neg_answ, neg_off };
	}
}