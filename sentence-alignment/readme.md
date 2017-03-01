
# Help:
	java -cp target/classes:target/lib/* Sentence_Aligner help
	
# 1. Length-based Phase
	java -cp target/classes:target/lib/* Sentence_Aligner length-based srcFile trgFile threshold

# 2. Train IBM1
	java -cp target/classes:target/lib/* Sentence_Aligner ibm1 srcFile trgFile loop

# 3. Length-and-Word-based Phase
	java -cp target/classes:target/lib/* Sentence_Aligner length-and-word-based srcFile trgFile threshold searchinglimit

# 4. Train Full Model
	java -cp target/classes:target/lib/* Sentence_Aligner train-full srcFile trgFile threshold1 threshold2 searchinglimit
	
train with folder

# 1. Length-based Phase
	java -cp target/classes:target/lib/* Sentence_Aligner length-based-folder indir srclang trglang threshold

# 2. Train IBM1
	java -cp target/classes:target/lib/* Sentence_Aligner ibm1-folder loop

# 3. Length-and-Word-based Phase
	java -cp target/classes:target/lib/* Sentence_Aligner length-and-word-based-folder threshold searchinglimit outdir
	
# 4. Train Full Model
	java -cp target/classes:target/lib/* Sentence_Aligner train-full-folder inDir srclang trglang threshold1 threshold2 searchinglimit outDir

# References
The program is implemented from this paper:

Robert C. Moore (2002): Fast and Accurate Sentence Alignment of Bilingual Corpora, Machine Translation: From Research to Real Users, 5th Conference of the Association for Machine Translation in the Americas, AMTA 2002 Tiburon, CA, USA, October 6-12, 2002, Proceedings
