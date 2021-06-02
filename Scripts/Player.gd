extends KinematicBody2D

#variáveis de status
var life = 3;

#variáveis de física
var run_speed = 350
var jump_speed = -1000
var gravity = 2500

var velocity = Vector2()

func Move():
	velocity.x = 0
	var right = Input.is_action_pressed('ui_right')
	var left = Input.is_action_pressed('ui_left')
	var jump = Input.is_action_just_pressed('ui_up')

	if is_on_floor() and jump:
		velocity.y = jump_speed
	if right:
		velocity.x += run_speed
		$cat.flip_h = true
		$collision.position.x = 10
		$pos_fish.position.x = 50

	if left:
		velocity.x -= run_speed
		$cat.flip_h = false
		$collision.position.x = -10
		$pos_fish.position.x = -50

func _physics_process(delta):
	velocity.y += gravity * delta
	Move()
	velocity = move_and_slide(velocity, Vector2.UP)
