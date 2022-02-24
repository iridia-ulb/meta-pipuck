LINUX_VERSION ?= "5.10.83"
LINUX_RPI_BRANCH ?= "rpi-5.10.y"
LINUX_RPI_KMETA_BRANCH ?= "yocto-5.10"

SRCREV_machine = "111a297d94e361de88d04b574acbca1bd5858cdb"
SRCREV_meta = "e1979ceb171bc91ef2cb71cfcde548a101dab687"

KMETA = "kernel-meta"

SRC_URI = " \
    git://github.com/raspberrypi/linux.git;name=machine;branch=${LINUX_RPI_BRANCH};protocol=https \
    git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=${LINUX_RPI_KMETA_BRANCH};destsuffix=${KMETA} \
    file://0001-Use-IIO-from-Analog-Devices.patch \
    file://0002-Add-E-Puck-multi-function-device.patch \
    file://0003-Add-E-Puck-ground-sensor.patch \
    file://0004-Add-E-Puck-camera-configuration-driver.patch \
    file://0005-Add-Pi-Puck-device-tree.patch \
    file://powersave.cfg \
    file://cryptography.cfg \
    file://epuck.cfg \
"

require linux-raspberrypi.inc

KERNEL_MODULE_AUTOLOAD += " iio-trig-sysfs"

KERNEL_DTC_FLAGS += "-@ -H epapr"
