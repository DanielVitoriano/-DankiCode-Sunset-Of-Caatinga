extends StaticBody2D

var pre_particle = preload("res://Scenes/cloud_particles.tscn")
var max_jumps = 5
var jumps = 0

# Called when the node enters the scene tree for the first time.
func _ready():
	pass # Replace with function body.

func _on_Area2D_body_entered(_body):
	var particle = pre_particle.instance()
	particle.global_position = $pos.global_position
	get_parent().add_child(particle)
	jumps += 1
	$".".scale -= Vector2(0.05, 0.05)
	particle.scale = $".".scale
	if jumps >= max_jumps:
		queue_free()
