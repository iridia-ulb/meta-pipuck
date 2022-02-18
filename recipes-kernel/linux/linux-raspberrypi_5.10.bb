LINUX_VERSION ?= "5.10.83"
LINUX_RPI_BRANCH ?= "rpi-5.10.y"
LINUX_RPI_KMETA_BRANCH ?= "yocto-5.10"

SRCREV_machine = "111a297d94e361de88d04b574acbca1bd5858cdb"
SRCREV_meta = "e1979ceb171bc91ef2cb71cfcde548a101dab687"

KMETA = "kernel-meta"

SRC_URI = " \
    git://github.com/raspberrypi/linux.git;name=machine;branch=${LINUX_RPI_BRANCH};protocol=https \
    git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=${LINUX_RPI_KMETA_BRANCH};destsuffix=${KMETA} \
    file://0003-iio-Add-support-for-low-speed-output-buffers.patch \
    file://0004-mfd-Add-support-for-the-E-Puck-mobile-robot.patch \
    file://0005-iio-Add-E-Puck-ground-sensor.patch \
    file://0006-media-Add-E-Puck-camera-configuration-driver.patch \
    file://0007-dts-Add-Pi-Puck-device-tree-overlay.patch \
    file://powersave.cfg \
    file://cryptography.cfg \
    file://epuck.cfg \
"

require linux-raspberrypi.inc

KERNEL_DTC_FLAGS += "-@ -H epapr"
