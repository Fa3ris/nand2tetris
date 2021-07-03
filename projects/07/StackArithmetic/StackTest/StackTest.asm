// push constant 17
@17
D=A	// D = 17
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 17
// push constant 17
@17
D=A	// D = 17
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 17
// eq 0
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
D=M-D	// D = *(SP - 1) - D
M=0	// *(SP - 1) = false, assume it is false
@EQ_END_0
D;JNE	// if D != 0, it is really false, finish
@SP
A=M-1
M=-1	// else set true
(EQ_END_0)
// push constant 17
@17
D=A	// D = 17
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 17
// push constant 16
@16
D=A	// D = 16
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 16
// eq 1
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
D=M-D	// D = *(SP - 1) - D
M=0	// *(SP - 1) = false, assume it is false
@EQ_END_1
D;JNE	// if D != 0, it is really false, finish
@SP
A=M-1
M=-1	// else set true
(EQ_END_1)
// push constant 16
@16
D=A	// D = 16
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 16
// push constant 17
@17
D=A	// D = 17
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 17
// eq 2
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
D=M-D	// D = *(SP - 1) - D
M=0	// *(SP - 1) = false, assume it is false
@EQ_END_2
D;JNE	// if D != 0, it is really false, finish
@SP
A=M-1
M=-1	// else set true
(EQ_END_2)
// push constant 892
@892
D=A	// D = 892
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 892
// push constant 891
@891
D=A	// D = 891
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 891
// lt 0
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
D=M-D	// D = *(SP - 1) - D
M=0	// *(SP - 1) = false, assume it is false
@LT_END_0
D;JGE	// if D >= 0, it is really false, finish
@SP
A=M-1
M=-1	// else set true
(LT_END_0)
// push constant 891
@891
D=A	// D = 891
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 891
// push constant 892
@892
D=A	// D = 892
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 892
// lt 1
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
D=M-D	// D = *(SP - 1) - D
M=0	// *(SP - 1) = false, assume it is false
@LT_END_1
D;JGE	// if D >= 0, it is really false, finish
@SP
A=M-1
M=-1	// else set true
(LT_END_1)
// push constant 891
@891
D=A	// D = 891
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 891
// push constant 891
@891
D=A	// D = 891
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 891
// lt 2
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
D=M-D	// D = *(SP - 1) - D
M=0	// *(SP - 1) = false, assume it is false
@LT_END_2
D;JGE	// if D >= 0, it is really false, finish
@SP
A=M-1
M=-1	// else set true
(LT_END_2)
// push constant 32767
@32767
D=A	// D = 32767
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 32767
// push constant 32766
@32766
D=A	// D = 32766
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 32766
// gt 0
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
D=M-D	// D = *(SP - 1) - D
M=0	// *(SP - 1) = false, assume it is false
@GT_END_0
D;JLE	// if D <= 0, it is really false, finish
@SP
A=M-1
M=-1	// else set true
(GT_END_0)
// push constant 32766
@32766
D=A	// D = 32766
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 32766
// push constant 32767
@32767
D=A	// D = 32767
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 32767
// gt 1
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
D=M-D	// D = *(SP - 1) - D
M=0	// *(SP - 1) = false, assume it is false
@GT_END_1
D;JLE	// if D <= 0, it is really false, finish
@SP
A=M-1
M=-1	// else set true
(GT_END_1)
// push constant 32766
@32766
D=A	// D = 32766
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 32766
// push constant 32766
@32766
D=A	// D = 32766
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 32766
// gt 2
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
D=M-D	// D = *(SP - 1) - D
M=0	// *(SP - 1) = false, assume it is false
@GT_END_2
D;JLE	// if D <= 0, it is really false, finish
@SP
A=M-1
M=-1	// else set true
(GT_END_2)
// push constant 57
@57
D=A	// D = 57
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 57
// push constant 31
@31
D=A	// D = 31
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 31
// push constant 53
@53
D=A	// D = 53
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 53
// add
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=D+M	// *(SP - 1) = D + [*(SP - 1)]
// push constant 112
@112
D=A	// D = 112
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 112
// sub
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=M-D	// *(SP - 1) = *(SP - 1) - D
// neg
@SP
A=M-1	// A = SP - 1
M=-M	// *(SP - 1) = -[*(SP - 1)]
// and
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=D&M	// *(SP - 1) = D & [*(SP - 1)]
// push constant 82
@82
D=A	// D = 82
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 82
// or
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=D|M	// *(SP - 1) = D | [*(SP - 1)]
// not
@SP
A=M-1	// A = SP - 1
M=!M	// *(SP - 1) = ![*(SP - 1)]
