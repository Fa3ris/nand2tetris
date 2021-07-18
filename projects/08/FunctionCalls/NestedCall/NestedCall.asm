// function Sys.init 0
(Sys.init)
@0
D=A	// D = 0
(Sys.init_INIT_LOOP)
@Sys.init_INIT_END
D;JEQ	// end if pushed all local vars
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=0	// push constant 0
D=D-1
@Sys.init_INIT_LOOP
0;JMP	// loop
(Sys.init_INIT_END)
// push constant 4000
@4000
D=A	// D = 4000
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 4000
// pop pointer 0
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@THIS
M=D	// THIS = *SP
// push constant 5000
@5000
D=A	// D = 5000
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 5000
// pop pointer 1
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@THAT
M=D	// THAT = *SP
// call Sys.main 0
// save Sys.init$ret.0
@Sys.init$ret.0
D=A
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = Sys.init$ret.0
// save LCL
@LCL
D=M
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = LCL
// save ARG
@ARG
D=M
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = ARG
// save THIS
@THIS
D=M
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = THIS
// save THAT
@THAT
D=M
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = THAT
@SP
D=M	// D = SP
@LCL
M=D	// LCL = SP
@5
D=D-A	// D = SP - (5 + 0)
@ARG
M=D	// ARG = SP - (5 + 0)
@Sys.main
0;JMP	// goto Sys.main
(Sys.init$ret.0)
// pop temp 1
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@6	// A = 5 + 1
M=D	// *(5 + 1) = *SP
// label LOOP
(Sys.init$LOOP)
// goto LOOP
@Sys.init$LOOP
0;JMP	// jump to LOOP
// function Sys.main 5
(Sys.main)
@5
D=A	// D = 5
(Sys.main_INIT_LOOP)
@Sys.main_INIT_END
D;JEQ	// end if pushed all local vars
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=0	// push constant 0
D=D-1
@Sys.main_INIT_LOOP
0;JMP	// loop
(Sys.main_INIT_END)
// push constant 4001
@4001
D=A	// D = 4001
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 4001
// pop pointer 0
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@THIS
M=D	// THIS = *SP
// push constant 5001
@5001
D=A	// D = 5001
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 5001
// pop pointer 1
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@THAT
M=D	// THAT = *SP
// push constant 200
@200
D=A	// D = 200
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 200
// pop local 1
@LCL
D=M	// D = LCL
@1
D=D+A	// D = LCL + 1
@R13
M=D	// R13 = LCL + 1
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@R13
A=M	// A = LCL + 1
M=D	// *(LCL + 1) = *SP
// push constant 40
@40
D=A	// D = 40
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 40
// pop local 2
@LCL
D=M	// D = LCL
@2
D=D+A	// D = LCL + 2
@R13
M=D	// R13 = LCL + 2
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@R13
A=M	// A = LCL + 2
M=D	// *(LCL + 2) = *SP
// push constant 6
@6
D=A	// D = 6
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 6
// pop local 3
@LCL
D=M	// D = LCL
@3
D=D+A	// D = LCL + 3
@R13
M=D	// R13 = LCL + 3
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@R13
A=M	// A = LCL + 3
M=D	// *(LCL + 3) = *SP
// push constant 123
@123
D=A	// D = 123
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 123
// call Sys.add12 1
// save Sys.main$ret.0
@Sys.main$ret.0
D=A
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = Sys.main$ret.0
// save LCL
@LCL
D=M
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = LCL
// save ARG
@ARG
D=M
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = ARG
// save THIS
@THIS
D=M
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = THIS
// save THAT
@THAT
D=M
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = THAT
@SP
D=M	// D = SP
@LCL
M=D	// LCL = SP
@6
D=D-A	// D = SP - (5 + 1)
@ARG
M=D	// ARG = SP - (5 + 1)
@Sys.add12
0;JMP	// goto Sys.add12
(Sys.main$ret.0)
// pop temp 0
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@5	// A = 5 + 0
M=D	// *(5 + 0) = *SP
// push local 0
@LCL
D=M	// D = LCL
@0
A=D+A	// A = LCL + 0
D=M	// D = *(LCL + 0)
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = *(LCL + 0)
// push local 1
@LCL
D=M	// D = LCL
@1
A=D+A	// A = LCL + 1
D=M	// D = *(LCL + 1)
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = *(LCL + 1)
// push local 2
@LCL
D=M	// D = LCL
@2
A=D+A	// A = LCL + 2
D=M	// D = *(LCL + 2)
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = *(LCL + 2)
// push local 3
@LCL
D=M	// D = LCL
@3
A=D+A	// A = LCL + 3
D=M	// D = *(LCL + 3)
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = *(LCL + 3)
// push local 4
@LCL
D=M	// D = LCL
@4
A=D+A	// A = LCL + 4
D=M	// D = *(LCL + 4)
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = *(LCL + 4)
// add
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=D+M	// *(SP - 1) = D + [*(SP - 1)]
// add
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=D+M	// *(SP - 1) = D + [*(SP - 1)]
// add
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=D+M	// *(SP - 1) = D + [*(SP - 1)]
// add
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=D+M	// *(SP - 1) = D + [*(SP - 1)]
// return
// save endFrame
@LCL
D=M	// D = LCL
@R13
M=D	// endFrame = LCL
// save return address
@5
A=D-A	// A = LCL - 5
D=M	// D = *(LCL - 5)
@R14
M=D	// retAddr = *(LCL - 5)
// put return value at ARG[0]
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@ARG
A=M	// A = ARG
M=D	// *ARG = *SP
// restore SP
D=A+1	// D = ARG + 1
@SP
M=D	// SP = ARG + 1
// restore THAT
@R13
AM=M-1	// A = --endFrame
D=M	// D = *endFrame
@THAT
M=D	// THAT = *endFrame
// restore THIS
@R13
AM=M-1	// A = --endFrame
D=M	// D = *endFrame
@THIS
M=D	// THIS = *endFrame
// restore ARG
@R13
AM=M-1	// A = --endFrame
D=M	// D = *endFrame
@ARG
M=D	// ARG = *endFrame
// restore LCL
@R13
AM=M-1	// A = --endFrame
D=M	// D = *endFrame
@LCL
M=D	// LCL = *endFrame
// goto return address
@R14
A=M	// A = retAddr
0;JMP	//  goto retAddr
// function Sys.add12 0
(Sys.add12)
@0
D=A	// D = 0
(Sys.add12_INIT_LOOP)
@Sys.add12_INIT_END
D;JEQ	// end if pushed all local vars
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=0	// push constant 0
D=D-1
@Sys.add12_INIT_LOOP
0;JMP	// loop
(Sys.add12_INIT_END)
// push constant 4002
@4002
D=A	// D = 4002
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 4002
// pop pointer 0
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@THIS
M=D	// THIS = *SP
// push constant 5002
@5002
D=A	// D = 5002
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 5002
// pop pointer 1
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@THAT
M=D	// THAT = *SP
// push argument 0
@ARG
D=M	// D = ARG
@0
A=D+A	// A = ARG + 0
D=M	// D = *(ARG + 0)
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = *(ARG + 0)
// push constant 12
@12
D=A	// D = 12
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 12
// add
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=D+M	// *(SP - 1) = D + [*(SP - 1)]
// return
// save endFrame
@LCL
D=M	// D = LCL
@R13
M=D	// endFrame = LCL
// save return address
@5
A=D-A	// A = LCL - 5
D=M	// D = *(LCL - 5)
@R14
M=D	// retAddr = *(LCL - 5)
// put return value at ARG[0]
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@ARG
A=M	// A = ARG
M=D	// *ARG = *SP
// restore SP
D=A+1	// D = ARG + 1
@SP
M=D	// SP = ARG + 1
// restore THAT
@R13
AM=M-1	// A = --endFrame
D=M	// D = *endFrame
@THAT
M=D	// THAT = *endFrame
// restore THIS
@R13
AM=M-1	// A = --endFrame
D=M	// D = *endFrame
@THIS
M=D	// THIS = *endFrame
// restore ARG
@R13
AM=M-1	// A = --endFrame
D=M	// D = *endFrame
@ARG
M=D	// ARG = *endFrame
// restore LCL
@R13
AM=M-1	// A = --endFrame
D=M	// D = *endFrame
@LCL
M=D	// LCL = *endFrame
// goto return address
@R14
A=M	// A = retAddr
0;JMP	//  goto retAddr
