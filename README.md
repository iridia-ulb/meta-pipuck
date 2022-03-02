# The meta-pipuck layer for the Yocto build system

## Description
The [meta-pipuck repository](https://github.com/iridia-ulb/meta-pipuck) contains a layer for the Yocto build system, which generates a complete, bootable Linux OS ready to be run on the Pi-Puck mobile robot. This layer is based on the [meta-raspberrypi](https://github.com/agherzan/meta-raspberrypi) layer. The system comes preinstalled with:
- ARGoS3 and a plugin for the Pi-Puck
- Python3

## Quick start
To ensure reproducible builds on systems with varying configurations, the following steps will explain how to create an image using [Docker](https://docs.docker.com/get-docker/). Note that you will probably need to use sudo or to switch to a root user to install Docker or to create and run its containers.

To get started, you first need to decide where you want to build the system for the Pi-Puck. Keep in mind that while the resulting image will be between 200-300 megabytes in size, the build system itself will require around **50 GB** of free disk space. The following steps will set up the build system. These steps assume that the build location is `/home/$(id -un)/yocto-pipuck` where `$(id -un)` will be replaced with the current username.
```sh
# Create a directory for the build system
mkdir /home/$(id -un)/yocto-pipuck
```

We now need to clone the layers for the build system as follows:
```sh
# Switch to the build location
cd /home/$(id -un)/yocto-pipuck
# Clone the Yocto repository
git clone git://git.yoctoproject.org/poky \
 --branch honister --single-branch
# Clone additional layers inside the Yocto repository
cd poky
git clone git://git.openembedded.org/meta-openembedded \
 --branch honister --single-branch
git clone https://github.com/iridia-ulb/meta-pipuck.git
```

### Create the Docker image
The following command will execute the Dockerfile in the meta-pipuck repository and create a Docker image based on Ubuntu 20.04 LTS. The image will contain a user and a group, which match the identifiers of current user and group. Setting the user and group in this way enables trivial access to the build system from the host.
```sh
sudo docker build -t yocto-pipuck:latest \
 https://github.com/iridia-ulb/meta-pipuck.git#:docker \
 --build-arg host_user_id=$(id -u) \
 --build-arg host_group_id=$(id -g)
```

### Create the Docker container
Once the above command has completed successfully, you can run the following command to create a container from the image. Note the two paths given after the `-v` option. The format of this argument is `path/on/host:path/in/container` where `path/on/host` is a directory on your host system and `path/in/container` is a directory inside the Docker container. This command will map the home directory inside the container to a directory called `yocto-pipuck` under the current user's home directory on the host.
```sh
sudo docker create --tty --interactive \
 --volume /home/$(id -un)/yocto-pipuck:/home/developer \
 --name yocto-pipuck \
 --hostname yocto-pipuck yocto-pipuck:latest
```
After executing this command, you should have a new container with the build environment. The following commands will start and attach to that container.

```sh
sudo docker start yocto-pipuck
sudo docker attach yocto-pipuck
```

### Start the build
After following the steps above, you should have a terminal that is attached to the docker container and be inside a directory called `build`. To build the entire image for the Pi-Puck, just run the following command:
```sh
bitbake pipuck-image-base
```

Occasionally, the build can fail due to internet connectivity issues or due to an oversight in the dependency tree. These issues are normally resolved by just re-executing the command above.

## Copying the image
The most straightforward way to burn a bootable image to the SD card is to use `bmaptool` from Intel. On Ubuntu, this package can be installed with `sudo apt install bmap-tools`. Most distributions of Linux should have a similar package that can be installed.

To burn the image, you need to locate the output image from the build system and to identify the device to which you would like to copy the image. The output image should be located under `yocto-pipuck/poky/build/tmp/deploy/images/raspberrypi0-wifi`. The device (probably an SD card) that you want to write to will usually be something like `/dev/sdX` or `/dev/mmcblkX`. The easiest way to find out is to inspect the output of `dmesg` before and after inserting the SD card into your computer. You will need to unmount the device before burning the image. Be careful -- writing the image to the wrong device will result in data loss.

```sh
umount /dev/DEVICE*
sudo bmaptool copy PATH/TO/pipuck-image-base-raspberrypi0-wifi.wic.bmap \
 /dev/DEVICE
```

## Booting the image and accessing the console
The easiest and most reliable way to get access to the Pi-Puck is by using the on-board serial-to-USB converter. You can then connect to the board using a terminal application such as Picocom as follows:
```sh
picocom -b 115200 /dev/ttyUSBX
```
Where `ttyUSBX` is the serial-to-USB converter. Check `dmesg` while attaching the cable to confirm that you have the right device. Note that to access the serial port, you will either have to (i) use `sudo`, (ii) switch to the root user, or (iii) add yourself to the `dialout` group (do not forget to restart afterwards).

## Wifi configuration
The wireless connection is controlled using the `iwctl` command. This interactive command makes the process of connecting to a wireless network relatively painless. Once you are connected, the wireless network is saved on the Pi-Puck under `/var/lib/iwd/SSID.KEY_TYPE`. By default, the Pi-Puck will connect to a network with the name `MergeableNervousSystem` using PSK authentication with the password `uprising`. The network should automatically connect on boot and fetch an IP address using DHCP.

