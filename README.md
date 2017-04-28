isar - Integration System for Automated Root filesystem generation

Isar is a set of scripts for building software packages and repeatable
generation of Debian-based root filesystems with customizations.

# Build

1. Install and configure sudo (see TODO):

        # apt-get install sudo
        # visudo

   In the editor, allow the current user to run sudo without a password, e.g.:

        <user>  ALL=NOPASSWD: ALL

   Replace <user> with your user name. Use the tab character between <user> and
   parameters.

1. Initialize the build directory for the target architecture:

        $ source isar-init-build-env build-amd64
        $ bitbake multiconfig:qemu-amd64:isar-image-base

        $ source isar-init-build-env build-i386
        $ bitbake multiconfig:qemu-i386:isar-image-base

        $ source isar-init-build-env build-rpi
        $ bitbake multiconfig:rpi:isar-image-base

1. Generate the disk image:

Install and configure `sudo` as explained in the previous section. Also make sure your system has the following
Debian packages installed:

       grub2
       grub-efi-amd64-bin
       grub-efi-ia32-bin
       gdisk

After the build has finished, use `wic` to generate a disk image, according to the target architecture:

	$ wic create sdimage-efi -o . -e multiconfig:qemu-amd64:isar-image-base
	$ wic create sdimage-efi -o . -e multiconfig:qemu-i386:isar-image-base
	$ wic create sdimage-raspberrypi -o . -e multiconfig:rpi:isar-image-base

The path to the generated image will be printed by `wic` once it has been generated.

# Try

The following commands allow testing the disk images with `qemu`:

	$ qemu-system-i386 -m 256M -nographic -bios /path/to/ovmf_code_ia32.bin -hda /path/to/sdimage-efi.direct
	$ qemu-system-x86_64 -m 256M -nographic -bios /path/to/ovmf_code_x64.bin -hda /path/to/sdimage-efi.direct

In order to run the generated images, a compiled version of the Tianocore firmware is required (`ovmf_code_*.bin`):

       https://github.com/tianocore/edk2/tree/3858b4a1ff09d3243fea8d07bd135478237cb8f7

Note that the `ovmf` package in Debian `jessie`/`stretch`/`sid` contains a pre-compiled firmware, but doesn't seem to be recent
enough to allow ISAR images to be testable under `qemu`.

To test the RPi board, flash the image to an SD card using the insctructions from the official site,
section "WRITING AN IMAGE TO THE SD CARD":

    https://www.raspberrypi.org/documentation/installation/installing-images/README.md

The default root password is 'root'.

# Release Information

Built on:
* Debian 8.2

Tested on:
* QEMU 1.1.2+dfsg-6a+deb7u12
* Raspberry Pi 1 Model B
