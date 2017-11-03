# This software is a part of ISAR.
# Copyright (C) 2015-2016 ilbers GmbH

inherit dpkg-base

# Build package from sources using build script
dpkg_runbuild() {
    ${PROOT} -0 \
             ${PROOT_EXTRA_ARGS} \
             -b /proc \
             -b /dev \
             -b ${WORKDIR}:${PP} \
             -r ${BUILDCHROOT_DIR} \
             /build.sh ${PP}/${SRC_DIR}
}
