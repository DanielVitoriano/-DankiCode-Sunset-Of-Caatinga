extends KinematicBody2D

#variáveis de status
var life = 3;

#variáveis de física
var run_speed = 300
var jump_speed = -1000
var gravity = 2500
var dir_fish = Vector2(1, 0)
var rot_fish = true
#demais
var can_move = true
var can_fire = true

#preloads
var pre_fish = preload("res://Scenes/bullet_fish.tscn")

var velocity = Vector2()

func Move():
	if can_move:
		velocity.x = 0
		var right = Input.is_action_pressed('ui_right')
		var left = Input.is_action_pressed('ui_left')
		var jump = Input.is_action_just_pressed('ui_up')

		if is_on_floor() and jump:
			velocity.y = jump_speed
		
		if right:
			dir_fish = Vector2(1, 0)
			rot_fish = true
			velocity.x += run_speed
			$cat.flip_h = false
			$collision.position.x = 10
			$pos_fish.position.x = 50
			$pos_fish.rotation = 0
			
		elif left:
			dir_fish = Vector2(-1 , 0)
			rot_fish = false
			velocity.x -= run_speed
			$cat.flip_h = true
			$collision.position.x = -10
			$pos_fish.position.x = -50
			$pos_fish.rotation = - 180


func _physics_process(delta):
	
	#animações
	if is_on_floor():
		$anim.play("idle")
		
	elif !is_on_floor():
		$anim.play("jump")
	
	velocity.y += gravity * delta
	Move()
	Fire()
	velocity = move_and_slide(velocity, Vector2.UP)

func Fire():
	if Input.is_action_just_pressed("ui_fire") and can_fire:
		var fish = pre_fish.instance()
		fish.global_position = $pos_fish.global_position
		fish.dir = dir_fish
		fish.FlipFish(rot_fish)
		get_parent().add_child(fish)

func play_on_enter():
	$anim.stop()
	$anim.play("on_enter")

