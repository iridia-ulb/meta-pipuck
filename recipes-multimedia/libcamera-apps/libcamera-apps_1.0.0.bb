SUMMARY = "Libcamera apps"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

DEPENDS = "libcamera libexif libpng tiff"
RDEPENDS_${PN} = "libcamera libexif libpng tiff"

inherit cmake

SRC_URI = " \
	git://github.com/raspberrypi/libcamera-apps.git;protocol=http;branch=main \
	file://0001-Set-library-SO-versions.patch \
	file://0002-Ignore-utils.patch \
"

SRCREV = "11c5203a202777ddc339bba1dd8f85b53b60c279"

S = "${WORKDIR}/git"

OECMAKE_SOURCEPATH = "${S}"

