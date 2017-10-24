# Root filesystem for packages building
#
# This software is a part of ISAR.
# Copyright (C) 2015-2016 ilbers GmbH

DESCRIPTION = "Multistrap development filesystem"

LICENSE = "gpl-2.0"
LIC_FILES_CHKSUM = "file://${LAYERDIR_isar}/licenses/COPYING.GPLv2;md5=751419260aa954499f7abaabaa882bbe"

FILESPATH =. "${LAYERDIR_core}/recipes-devtools/buildchroot/files:"
SRC_URI = "file://multistrap.conf.in \
           file://configscript.sh \
           file://setup.sh \
           file://download_dev-random \
           file://build.sh"
PV = "1.0"

BUILDCHROOT_PREINSTALL ?= "gcc \
                           make \
                           build-essential \
                           debhelper \
                           autotools-dev \
                           dpkg \
                           locales \
                           docbook-to-man \
                           apt \
                           automake"

WORKDIR = "${TMPDIR}/work/${DISTRO}-${DISTRO_ARCH}/${PN}"

do_build[stamp-extra-info] = "${DISTRO}-${DISTRO_ARCH}"
do_build[dirs] = "${WORKDIR}/hooks_multistrap"

do_build() {
    chmod +x "${WORKDIR}/setup.sh"
    chmod +x "${WORKDIR}/configscript.sh"
    install -m 755 "${WORKDIR}/download_dev-random" "${WORKDIR}/hooks_multistrap/"

    # Multistrap accepts only relative path in configuration files, so get it:
    cd ${TOPDIR}
    WORKDIR_REL=${@ os.path.relpath(d.getVar("WORKDIR", True))}

    # Adjust multistrap config
    sed -e 's|##BUILDCHROOT_PREINSTALL##|${BUILDCHROOT_PREINSTALL}|g' \
        -e 's|##DISTRO##|${DISTRO}|g' \
        -e 's|##DISTRO_APT_SOURCE##|${DISTRO_APT_SOURCE}|g' \
        -e 's|##DISTRO_SUITE##|${DISTRO_SUITE}|g' \
        -e 's|##DISTRO_COMPONENTS##|${DISTRO_COMPONENTS}|g' \
        -e 's|##CONFIG_SCRIPT##|./'"$WORKDIR_REL"'/configscript.sh|g' \
        -e 's|##SETUP_SCRIPT##|./'"$WORKDIR_REL"'/setup.sh|g' \
        -e 's|##DIR_HOOKS##|./'"$WORKDIR_REL"'/hooks_multistrap|g' \
           "${WORKDIR}/multistrap.conf.in" > "${WORKDIR}/multistrap.conf"

    # Create root filesystem
    PROOT_NO_SECCOMP=1 proot -0 multistrap -a ${DISTRO_ARCH} -d "${BUILDCHROOT_DIR}" -f "${WORKDIR}/multistrap.conf"

    # Install package builder script
    install -m 755 ${WORKDIR}/build.sh ${BUILDCHROOT_DIR}

    # Install resolv.conf to target
    cp /etc/resolv.conf ${BUILDCHROOT_DIR}/etc

    # Configure root filesystem
    PROOT_NO_SECCOMP=1 proot -0 ${PROOT_QEMU_ARGS} -b /proc -b /dev -r ${BUILDCHROOT_DIR} /configscript.sh
}
