DESCRIPTION = "Commented config.txt file for the Raspberry Pi. \
               The Raspberry Pi config.txt file is read by the GPU before \
               the ARM core is initialised. It can be used to set various \
               system configuration parameters."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

COMPATIBLE_MACHINE = "^rpi$"

SRCREV = "648ffc470824c43eb0d16c485f4c24816b32cd6f"
SRC_URI = "git://github.com/Evilpaul/RPi-config.git;protocol=https;branch=master \
          "

S = "${WORKDIR}/git"

PR = "r5"

INHIBIT_DEFAULT_DEPS = "1"

inherit deploy nopackages

do_deploy() {
    install -d ${DEPLOYDIR}/${BOOTFILES_DIR_NAME}
    CONFIG=${DEPLOYDIR}/${BOOTFILES_DIR_NAME}/config.txt

    touch $CONFIG

    # Video camera support
    echo "start_x=1" >> $CONFIG

    # Enable I2C
    echo "dtparam=i2c1=on" >>$CONFIG
    echo "dtparam=i2c_arm=on" >>$CONFIG

    # Enable UART
    echo "enable_uart=1" >> $CONFIG

    # Enable Video Core
    echo "dtoverlay=vc4-kms-v3d" >> $CONFIG

    # Enable Omnivision RaspberryPi Camera
    echo "dtoverlay=ov5647" >> $CONFIG

    # Enable Pi-puck device tree overlay
    echo "dtoverlay=pi-puck" >> $CONFIG

    # Set GPIO 5 (pin 29) to input,pull-down so board powers off after shutdown
    # Set GPIO 5 (pin 29) to output,high to keep board powered on until shutdown
    echo "gpio=5=ip,pd" >> $CONFIG
    echo "gpio=5=op,dh" >> $CONFIG

    # Set GPIO 6 (pin 31) to input,pull-up for power button
    echo "gpio=6=ip,pu" >> $CONFIG

    # Set GPIO 16,24 (pins 36,18) to output,high to release FT903 and e-puck resets
    echo "gpio=16,24=op,dh" >> $CONFIG

    # Set GPIO 13 (pins 33) to input,pull-none for charge detect input
    echo "gpio=13=ip,pn" >> $CONFIG

    # Set GPIO 22 (pins 15) to output,low for speaker enable (disabled by default)
    echo "gpio=22=op,dl" >> $CONFIG

}

addtask deploy before do_build after do_install
do_deploy[dirs] += "${DEPLOYDIR}/${BOOTFILES_DIR_NAME}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
