SUMMARY = "MJPG-streamer takes JPGs from Linux-UVC compatible webcams, filesystem or other input plugins and streams them as M-JPEG via HTTP to webbrowsers, VLC and other software."
SECTION = "graphics"
HOMEPAGE = "https://github.com/jacksonliam/mjpg-streamer/"
LICENSE="GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS = "v4l-utils"

inherit cmake

SRCREV="310b29f4a94c46652b20c4b7b6e5cf24e532af39"
SRC_URI = "git://github.com/jacksonliam/mjpg-streamer.git;protocol=git"

S = "${WORKDIR}/git/mjpg-streamer-experimental"

#EXTRA_OECMAKE = "-DENABLE_HTTP_MANAGEMENT=ON"

FILES_${PN} += "${libdir}/*.so"

