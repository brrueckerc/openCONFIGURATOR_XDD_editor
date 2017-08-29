/*******************************************************************************
 * @file   IPowerlinkConstants.java
 *
 * @author Sree Hari Vignesh, Kalycito Infotech Private Limited.
 *
 * @copyright (c) 2017, Kalycito Infotech Private Limited
 *                    All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *   * Neither the name of the copyright holders nor the
 *     names of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *******************************************************************************/

package com.br_automation.buoat.xddeditor.XDD.resources;

/**
 * POWERLINK constants.
 *
 * @author Sree Hari Vignesh
 *
 */
public interface IPowerlinkConstants {

    // Object index constants.
    public static final int COMMUNICATION_PROFILE_START_INDEX = 0x1000;
    public static final int COMMUNICATION_PROFILE_END_INDEX = 0x1FFF;
    public static final int MANUFACTURER_PROFILE_START_INDEX = 0x2000;
    public static final int MANUFACTURER_PROFILE_END_INDEX = 0x5FFF;
    public static final int STANDARDISED_DEVICE_PROFILE_START_INDEX = 0x6000;
    public static final int STANDARDISED_DEVICE_PROFILE_END_INDEX = 0x9FFF;
    public static final int STANDARDISED_INTERFACE_PROFILE_START_INDEX = 0xA000;
    public static final int STANDARDISED_INTERFACE_PROFILE_END_INDEX = 0xBFFF;

    // Modified date and time format.
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ssXXX";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    // Default value of lossOfSocTolerance in nano seconds.
    public static final long LOSS_OF_SOC_TOLERANCE_DEFAULT_VALUE = 100000L;
}
