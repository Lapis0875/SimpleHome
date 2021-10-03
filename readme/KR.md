# SimpleHome

간단하게 사용하는 위치 저장 플러그인.

기본 명령어 : /waypoint

## 1. 경유지 저장
경유지는 한개만 저장할 수 있습니다.
- /waypoint register : 현재 위치를 경유지로 저장합니다.
- /waypoint tp : 저장된 경유지로 tp합니다. 
(차원 또한 저장되어, 네더 등의 다른차원에서 오버월드의 경유지로 이동하는것 또한 가능합니다.)
- /waypoint show : 저장된 경유지와 사망 위치를 표시합니다. 없을경우 아무것도 표시되지 않습니다.

## 2. 사망 위치 저장
사망시, 사망 위치가 저장됩니다.
- /waypoint deathlocation show : 사망 위치를 표시합니다.
- /waypoint deathlocation tp : 사망 위치로 이동합니다. (`/waypoint tp` 처럼 차원 정보 또한 반영됩니다.)

## 3. 위치 삭제
명령어로 저장된 위치를 삭제할 수 있습니다.
- /waypoint clear waypoint : 저장된 경유지를 삭제합니다.
- /waypoint clear deathlocation : 저장된 사망위치를 삭제합니다.