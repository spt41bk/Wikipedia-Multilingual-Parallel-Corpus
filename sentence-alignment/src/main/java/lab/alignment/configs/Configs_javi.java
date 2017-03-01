/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.alignment.configs;

import static lab.alignment.configs.Configs.ROOT;

/**
 *
 * @author s1420211
 */
public interface Configs_javi {

    String WORK_DIR = ROOT + "/ja-vi";
//    final String regex = "[\\p{Punct}\\d･､]+";
    final String regex = "[\\p{Punct}\\d]+";

    public interface MODEL {

        String MODEL_DIR = WORK_DIR + "/model";
        String PHRASE_TABLE = MODEL_DIR + "/phrase-table";
    }

    public interface TRAIN {

        String TRAIN_DIR = WORK_DIR + "/train";

        public interface SEED {

            String SEED_DIR = TRAIN_DIR + "/seed";

            public interface EXTRACT {

                String EXTRACT_DIR = SEED_DIR + "/extract";

                String EN_COMMON = EXTRACT_DIR + "/phrases.common.en";

                String JAEN_TABLE = EXTRACT_DIR + "/phrase-table.ja-en";
                String ENVI_TABLE = EXTRACT_DIR + "/phrase-table.en-vi";
                String JA_PHRASES = EXTRACT_DIR + "/phrases.ja";
                String VI_PHRASES = EXTRACT_DIR + "/phrases.vi";

            }

            public interface INDEX {

                String INDEX_DIR = SEED_DIR + "/index";
                String JA_PHRASES = INDEX_DIR + "/phrase.ja";
                String EN_PHRASES = INDEX_DIR + "/phrase.en";
                String VI_PHRASES = INDEX_DIR + "/phrase.vi";
                String JAEN_TABLE = INDEX_DIR + "/phrase-table.ja-en";
                String ENVI_TABLE = INDEX_DIR + "/phrase-table.en-vi";
            }

            public interface PIVOT {

                String PIVOT_DIR = SEED_DIR + "/pivot";

                public interface SOURCE {

                    String SOURCE_DIR = PIVOT_DIR + "/source";
                    int SIZE = 80_000;
                    String JA_PHRASES = SOURCE_DIR + "/phrases.ja";

                }

                String SCORES_DIR = PIVOT_DIR + "/scores";
                String SCORES = PIVOT_DIR + "/pivot.scores";
                String SCORES_NORM = PIVOT_DIR + "/pivot.scores.norm";
                String JA_PHRASES = PIVOT_DIR + "/phrases.index.ja";
                String VI_PHRASES = PIVOT_DIR + "/phrases.index.vi";

            }

            public interface MODEL {

                String MODEL_DIR = SEED_DIR + "/model";
                String PHRASE_TABLE = MODEL_DIR + "/phrase-table";
                String PHRASE_TABLE_UNI = MODEL_DIR + "/phrase-table-add-uni";                
            }

            public interface UNIGRAM {

                String UNIGRAM_DIR = SEED_DIR + "/unigram";
                String WORD_PAIRS = UNIGRAM_DIR + "/word-pairs.ja.vi";
                String WORD_PAIRS_NORM = UNIGRAM_DIR + "/word-pairs.norm.ja.vi";                

            }

        }

        public interface PIVOT {

            String PIVOT_DIR = TRAIN_DIR + "/pivot";

            public interface SOURCE {

                String SOURCE_DIR = PIVOT_DIR + "/source";
                int SIZE = 300_000;
                String JA_PHRASES = SOURCE_DIR + "/phrases.ja";

            }

            String SCORES_DIR = PIVOT_DIR + "/scores";
            String SCORES = PIVOT_DIR + "/pivot.scores";
            String SCORES_NORM = PIVOT_DIR + "/pivot.scores.norm";
            String JA_PHRASES = PIVOT_DIR + "/phrases.index.ja";
            String VI_PHRASES = PIVOT_DIR + "/phrases.index.vi";

        }

        public interface INDEX {

            String INDEX_DIR = TRAIN_DIR + "/index";
            String JA_PHRASES = INDEX_DIR + "/phrase.ja";
            String EN_PHRASES = INDEX_DIR + "/phrase.en";
            String VI_PHRASES = INDEX_DIR + "/phrase.vi";
            String JAEN_TABLE = INDEX_DIR + "/phrase-table.ja-en";
            String ENVI_TABLE = INDEX_DIR + "/phrase-table.en-vi";
        }

        public interface EXTRACT {

            String EXTRACT_DIR = TRAIN_DIR + "/extract";

            String EN_COMMON = EXTRACT_DIR + "/phrases.common.en";

            String JAEN_TABLE = EXTRACT_DIR + "/phrase-table.ja-en";
            String ENVI_TABLE = EXTRACT_DIR + "/phrase-table.en-vi";
            String JA_PHRASES = EXTRACT_DIR + "/phrases.ja";
            String VI_PHRASES = EXTRACT_DIR + "/phrases.vi";

        }

    }

    public interface DATA {

        String DATA_DIR = WORK_DIR + "/data";

        public interface BIN {

            String BIN_DIR = DATA_DIR + "/bin";
            String JA_EN_TABLE = BIN_DIR + "/phrase-table-ja-en.bin";
            String EN_VI_TABLE = BIN_DIR + "/phrase-table-en-vi.bin";
            String JA_PHRASES = BIN_DIR + "/phrases.ja";
            String ja_EN_PHRASES = BIN_DIR + "/phrases.ja-en.en";
            String EN_vi_PHRASES = BIN_DIR + "/phrases.en-vi.en";
            String VI_PHRASES = BIN_DIR + "/phrases.vi";

            public interface EXTRACT {

                String EXTRACT_DIR = BIN_DIR + "/extract";

                String EN_COMMON = EXTRACT_DIR + "/phrases.common.en";

                String JAEN_TABLE = EXTRACT_DIR + "/phrase-table.ja-en";
                String ENVI_TABLE = EXTRACT_DIR + "/phrase-table.en-vi";
                String JA_PHRASES = EXTRACT_DIR + "/phrases.ja";
                String VI_PHRASES = EXTRACT_DIR + "/phrases.vi";
            }
        }

        public interface SEED {

            String SEED_DIR = DATA_DIR + "/seed";

            int CONNECT_LEN = 2;

            String JA_EN_TABLE = SEED_DIR + "/phrase-table-ja-en.bin";
            String EN_VI_TABLE = SEED_DIR + "/phrase-table-en-vi.bin";
            String JA_PHRASES = SEED_DIR + "/phrases.ja";
            String ja_EN_PHRASES = SEED_DIR + "/phrases.ja-en.en";
            String EN_vi_PHRASES = SEED_DIR + "/phrases.en-vi.en";
            String VI_PHRASES = SEED_DIR + "/phrases.vi";
            String JA_EN_TABLE_TXT = SEED_DIR + "/phrase-table-ja-en.txt";
            String EN_VI_TABLE_TXT = SEED_DIR + "/phrase-table-en-vi.txt";
        }

    }

    public interface TABLE {

        String TABLE_DIR = ROOT + "/table";
        String JA_EN_PHRASE_TABLE = TABLE_DIR + "/phrase-table-ja-en";
        String EN_VI_PHRASE_TABLE = TABLE_DIR + "/phrase-table-en-vi";

    }

}
