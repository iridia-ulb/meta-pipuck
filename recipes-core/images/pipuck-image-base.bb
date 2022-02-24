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
  libcamera \
  libcamera-apps \
  gstreamer1.0-plugins-good \
  gstreamer1.0-plugins-base \
  v4l-utils \
  fernbedienung \
  python3 \
  python3-modules \
"


