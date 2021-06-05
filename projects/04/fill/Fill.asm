// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// SCREEN = 16384

// KBD = 24576
(INIT)
    @selectedColor
    M=0     // selectedColor = white

    @currentColor
    M=0     // selectedColor = white

    // SCREEN is 256 rows x 512 cols
    // 512 cols = 32 x 16-bit registers

    // 256 rows
    // 32 registers / row
    // 256 * 32 = 8192 registers
    // from 0 to 8191
    @8191
    D=A
    @lastScreenSlot 
    M=D     // lastScreenSlot = 8191

(RESET)
    @FILL_SCREEN
    0;JMP

(START) // Infinite loop

    // check state of keyboard
    // if 0 set selectedColor to white = 0
    // else set selectedColor to black = -1
    @KBD
    D=M
    
    @SET_WHITE
    D;JEQ

    @SET_BLACK
    0;JMP

(SET_WHITE)
    @selectedColor
    M=0

    @CHECK_COLOR
    0;JMP

(SET_BLACK)
    @selectedColor
    M=-1

    @CHECK_COLOR
    0;JMP

(CHECK_COLOR)
    // compoare current and selected colors
    // if same do nothing and loop
    // else update currentColor and update screen
    @selectedColor
    D=M     // D = selectedColor

    @currentColor
    D=D-M   // D = selectedColor - currentColor

    @START
    D;JEQ   // if selectedColor - currentColor == 0 => selectedColor == currentColor 
            // => no screen update goto START

(UPDATE_CURRENT_COLOR)
    // else currentColor = selectedColor and fill screen
    @selectedColor
    D=M     // D = selectedColor

    @currentColor
    M=D     // currentColor = selectedColor

(FILL_SCREEN)
    @i
    M=0     // let i = 0

    @SCREEN
    D=A     // D = SCREEN

    @screenPointer  
    M=D     // let screenPointer = SCREEN

(LOOP)
    // load i
    @i
    D=M     // D = i

    @lastScreenSlot
    D=D-M   // D = i - lastScreenSlot 
    
    @START
    D;JGT   // if i - lastScreenSlot > 0 => i > lastScreenSlot 
            // => loop is finished goto START

(FILL_SLOT)
    @currentColor
    D=M     // load currentColor

    @screenPointer 
    A=M     // go to address pointed by screenPointer
    M=D     // fill pointed register with currentColor
            // RAM[RAM[screenPointer]] = currentColor

(INC_PTR)
    @screenPointer
    M=M+1   // screenPointer++

(INC_I)
    @i
    M=M+1   // i++


    @LOOP
    0;JMP   // loop