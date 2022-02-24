SUMMARY = "Linux libcamera framework"
SECTION = "libs"

LICENSE = "GPL-2.0+ & LGPL-2.1+"

LIC_FILES_CHKSUM = "\
    file://LICENSES/GPL-2.0-or-later.txt;md5=fed54355545ffd980b814dab4a3b312c \
    file://LICENSES/LGPL-2.1-or-later.txt;md5=2a4f4fd2128ea2f65047ee63fbca9f68 \
"

SRC_URI = " \
        git://linuxtv.org/libcamera.git;protocol=git;branch=master \
        file://0001-Rename-fd-to-get.patch \
        file://0002-Set-versions-of-shared-libraries.patch \
"

SRCREV = "560f5cf998646ddc54a20dc1c7326012834d3204"

PV = "202105+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "python3-pyyaml-native python3-jinja2-native python3-ply-native python3-jinja2-native udev gnutls boost chrpath-native"
DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'qt', 'qtbase qtbase-native', '', d)}"

PACKAGES =+ "${PN}-gst"

PACKAGECONFIG ??= "raspberrypi v4l2"
PACKAGECONFIG[gst] = "-Dgstreamer=enabled,-Dgstreamer=disabled,gstreamer1.0 gstreamer1.0-plugins-base"
PACKAGECONFIG[v4l2] = "-Dv4l2=true,-Dv4l2=false"
PACKAGECONFIG[raspberrypi] = "-Dpipelines=raspberrypi"

RDEPENDS:${PN} = "${@bb.utils.contains('DISTRO_FEATURES', 'wayland qt', 'qtwayland', '', d)}"

inherit meson pkgconfig python3native

do_configure:prepend() {
    sed -i -e 's|py_compile=True,||' ${S}/utils/ipc/mojo/public/tools/mojom/mojom/generate/template_expander.py
}

do_install:append() {
    chrpath -d ${D}${libdir}/libcamera.so
}

addtask do_recalculate_ipa_signatures_package after do_package before do_packagedata
do_recalculate_ipa_signatures_package() {
    local modules
    for module in $(find ${PKGD}/usr/lib/libcamera -name "*.so.sign"); do
        module="${module%.sign}"
        if [ -f "${module}" ] ; then
            modules="${modules} ${module}"
        fi
    done

    ${S}/src/ipa/ipa-sign-install.sh ${B}/src/ipa-priv-key.pem "${modules}"
}

FILES:${PN} += "${libdir}/v4l2-compat.so.*"
FILES:${PN}-dev += "${libdir}/v4l2-compat.so"

