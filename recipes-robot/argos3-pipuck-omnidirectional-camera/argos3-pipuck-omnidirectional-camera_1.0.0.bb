SUMMARY = "ARGoS3 Plug-in for the Pi-Puck's omni-directional camera"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

DEPENDS = "argos3-pipuck apriltag"
RDEPENDS_${PN} = "argos3-pipuck apriltag"

inherit cmake

SRC_URI = "git://github.com/iridia-ulb/argos3-pipuck-omnidirectional-camera.git;protocol=http"

SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

OECMAKE_SOURCEPATH = "${S}/src"

EXTRA_OECMAKE += "-DARGOS_BUILD_FOR=pi-puck"

# Since the base package name (BPN) is argos3-pipuck-omnidirectional-camera 
# and not argos3, Bitbake willnot automatically collect the files that we 
# install inside ${libdir}/argos3, ${datadir}/argos3, and ${includedir}/argos3.
FILES:${PN} += "${libdir}/argos3/*"
FILES:${PN} += "${datadir}/argos3/*"
FILES:${PN} += "${includedir}/argos3/*"

