# SimpleHome
[kr](readme/KR.md)

Simple way to save your waypoint.

기본 명령어 : /waypoint

## 1. Save waypoint
You can save only one waypoint.
- /waypoint register : Save current position as your waypoint.
- /waypoint tp : Teleport to saved waypoint
(Dimension-aware, so you can teleport to your waypoint(overworld) from other dimension(nether, the end, etc.).
- /waypoint show : Show saved waypoint and death location. No output message if both waypoint is not saved.

## 2. Save death location.
If you die, plugin will automatically save your death location.
- /waypoint deathlocation show : Show your death location.
- /waypoint deathlocation tp : Teleport to your death location. (Dimension-aware like `/waypoint tp`)

## 3. Remove Waypoint.
You can use these commands to manually remove waypoints.
- /waypoint clear waypoint : Remove waypoint.
- /waypoint clear deathlocation : Remove death location.