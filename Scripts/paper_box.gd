extends StaticBody2D


# Called when the node enters the scene tree for the first time.
func _ready():
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
#func _process(delta):
#	pass


func _on_enter_area_body_entered(body):
	body.can_move = false
	body.can_fire = false
	var gravity = body.gravity
	body.gravity = gravity
	body.global_position = $position.global_position
	body.play_on_enter()
