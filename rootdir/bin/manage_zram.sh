#!/vendor/bin/sh

ZRAM_SIZE=$(getprop persist.sys.zram.size)
ZRAM_COMPRESSION=$(getprop persist.sys.zram.compression)

# Check if properties are set, otherwise use defaults
ZRAM_SIZE=${ZRAM_SIZE:-2} # Default to 2GB
ZRAM_COMPRESSION=${ZRAM_COMPRESSION:-lz4} # Default to lz4

# Reset ZRAM device
echo 1 > /sys/block/zram0/reset

# Set ZRAM size
case "$ZRAM_SIZE" in
    0) echo 0 > /sys/block/zram0/disksize ;;
    2) echo 2147483648 > /sys/block/zram0/disksize ;; # 2GB
    4) echo 4294967296 > /sys/block/zram0/disksize ;; # 4GB
    8) echo 8589934592 > /sys/block/zram0/disksize ;; # 8GB
    *) echo 2147483648 > /sys/block/zram0/disksize ;; # Default to 2GB
esac

# Set ZRAM compression algorithm
echo "$ZRAM_COMPRESSION" > /sys/block/zram0/comp_algorithm

# Log the applied settings
log -p i -t ZRAM "Applied ZRAM settings: Size=$ZRAM_SIZE GB, Compression=$ZRAM_COMPRESSION"
