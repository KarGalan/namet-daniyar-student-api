#!/bin/bash

# Student: Daniyar Namet
# Group: IT2-2312 SE
# Student ID: 37056

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
LOG_FILE="$SCRIPT_DIR/../logs/Namet_Daniyar_system_report.log"

{
    echo "================================"
    echo "STUDENT SYSTEM REPORT"
    echo "================================"

    echo "Student: Daniyar Namet"
    echo "Group: IT2-2312 SE"
    echo "Student ID: 37056"

    echo "Date and Time: $(date)"
    echo "Username: $(whoami)"
    echo "Operating System: $(uname -s)"
    echo "Kernel Version: $(uname -r)"

    echo "CPU Model: $(grep -m 1 'model name' /proc/cpuinfo | cut -d: -f2- | xargs)"
    echo "CPU Cores: $(nproc)"

    echo "Memory Information:"
    free -h

    echo "Disk Usage:"
    df -h /

    echo "Available Disk Space: $(df -h / | awk 'NR==2 {print $4}')"

    echo "================================"
} | tee -a "$LOG_FILE"
