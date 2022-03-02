# Base this image on core-image-base
include recipes-core/images/core-image-base.bb

COMPATIBLE_MACHINE = "^rpi$"

# Additional packages
IMAGE_INSTALL:append = " \
  iwd \
  iw \
  i2c-tools \
  yavta \
  mjpg-streamer \
  argos3-pipuck \
  argos3-pipuck-omnidirectional-camera \
  libcamera \
  libcamera-apps \
  gstreamer1.0-plugins-good \
  gstreamer1.0-plugins-base \
  v4l-utils \
  fernbedienung \
  python3 \
  python3-modules \
"

set_local_timezone() {
    ln -sf /usr/share/zoneinfo/Europe/Brussels ${IMAGE_ROOTFS}/etc/localtime
}

set_motd() {
    printf "\nDo you want ants!? Because that's how you get ants!\n" > ${IMAGE_ROOTFS}/etc/motd
}

preload_libcamera_v4l2compat() {
    echo "export LD_PRELOAD=/usr/lib/v4l2-compat.so.0" >> ${IMAGE_ROOTFS}/etc/profile
}

ROOTFS_POSTPROCESS_COMMAND += " \
    set_local_timezone ; \
    set_motd ; \
    preload_libcamera_v4l2compat ; \
 "

