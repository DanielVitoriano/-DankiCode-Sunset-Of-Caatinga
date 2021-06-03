extends Area2D

var max_distance = 250

var speed = 300
var dano = 1
var dir = Vector2(0, -1) setget setDir
var live = true
onready var pos_initial = global_position

# Called when the node enters the scene tree for the first time.
func _ready():
	pass



func _process(delta):
	if live:
		translate(dir * speed * delta)
		if global_position.distance_to(pos_initial) > max_distance:
			autoDestroi()

func setDir(val):
	dir = val
	pass

func FlipFish(boolean):
	$fish_nothing.flip_h = boolean

func autoDestroi():
	queue_free()
