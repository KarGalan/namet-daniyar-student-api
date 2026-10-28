#!/usr/bin/env bash
set -euo pipefail

BASE_URL="http://localhost:8082"
RESPONSE_FILE="$(mktemp)"

trap 'rm -f "$RESPONSE_FILE"' EXIT

echo "========================================"
echo "DevOps Midterm - REST API Testing"
echo "Student: Daniyar Namet"
echo "Group: IT2-2312 SE"
echo "Student ID: 37056"
echo "========================================"

request() {
    local method="$1"
    local endpoint="$2"
    local expected="$3"
    local data="${4:-}"
    local status

    echo
    echo "Testing: $method $endpoint"

    if [ -n "$data" ]; then
        status=$(curl -sS \
            --connect-timeout 5 \
            --max-time 20 \
            -X "$method" \
            -H "Content-Type: application/json" \
            -d "$data" \
            -o "$RESPONSE_FILE" \
            -w "%{http_code}" \
            "$BASE_URL$endpoint")
    else
        status=$(curl -sS \
            --connect-timeout 5 \
            --max-time 20 \
            -X "$method" \
            -o "$RESPONSE_FILE" \
            -w "%{http_code}" \
            "$BASE_URL$endpoint")
    fi

    echo "HTTP Status: $status"
    echo "Expected:    $expected"

    if [ "$status" != "$expected" ]; then
        echo "FAILED: Unexpected HTTP status"
        cat "$RESPONSE_FILE"
        exit 1
    fi

    if [ -s "$RESPONSE_FILE" ]; then
        python3 -m json.tool "$RESPONSE_FILE" || \
            cat "$RESPONSE_FILE"
    fi

    echo "PASS"
}

# Test 1: CREATE
request POST "/api/students" 201 \
'{
    "fullName": "Daniyar Namet",
    "groupName": "IT2-2312 SE",
    "studentId": "37056"
}'

TEST_ID=$(python3 -c \
'import json,sys; print(json.load(open(sys.argv[1]))["id"])' \
"$RESPONSE_FILE")

echo "Created test record ID: $TEST_ID"

# Test 2: READ ALL
request GET "/api/students" 200

# Test 3: READ BY ID
request GET "/api/students/$TEST_ID" 200

# Test 4: UPDATE
request PUT "/api/students/$TEST_ID" 200 \
'{
    "fullName": "Daniyar Namet",
    "groupName": "IT2-2312 SE Updated",
    "studentId": "37056"
}'

python3 - "$RESPONSE_FILE" <<'PY'
import json
import sys

with open(sys.argv[1]) as f:
    student = json.load(f)

assert student["groupName"] == "IT2-2312 SE Updated"
print("Updated group verified successfully")
PY

# Test 5: DELETE
request DELETE "/api/students/$TEST_ID" 204

# Test 6: DELETED STUDENT
request GET "/api/students/$TEST_ID" 404

# Test 7: NONEXISTENT STUDENT
request GET "/api/students/999999999" 404

echo
echo "========================================"
echo "ALL API TESTS PASSED"
echo "POST   /api/students            201"
echo "GET    /api/students            200"
echo "GET    /api/students/{id}       200"
echo "PUT    /api/students/{id}       200"
echo "DELETE /api/students/{id}       204"
echo "GET    /api/students/{id}       404"
echo "GET    /api/students/999999999  404"
echo "Student: Daniyar Namet"
echo "Group: IT2-2312 SE"
echo "Student ID: 37056"
echo "========================================"
