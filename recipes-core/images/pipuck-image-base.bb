# Base this image on core-image-base
include recipes-core/images/core-image-base.bb

COMPATIBLE_MACHINE = "^rpi$"

# Additional packages
IMAGE_INSTALL_append = " iwd iw i2c-tools yavta mjpg-streamer argos3-pipuck argos3-pipuck-controller fernbedienung"


