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
public interface Configs_evs {

    String WORK_DIR = ROOT + "/evs";

    public interface DATA {

        String DATA_DIR = ROOT + "/data";

        String TEST_STATISTICS = DATA_DIR + "/test-data.statistics.txt";

        public interface TEST {

            String TEST1_DIR = DATA_DIR + "/test1";
            String TEST2_DIR = DATA_DIR + "/test2";
            String TEST2_TEST_EN = DATA_DIR + "/test2.test.en";
            String TEST2_TEST_VI = DATA_DIR + "/test2.test.vi";

            String TEST2_REF_EN = DATA_DIR + "/test2.ref.en";
            String TEST2_REF_VI = DATA_DIR + "/test2.ref.vi";

            String TEST2_VOCAB_EN = DATA_DIR + "/test2.vocb.en";
            String TEST2_VOCAB_VI = DATA_DIR + "/test2.vocab.vi";

        }

        public interface REF {

            String REF1_DIR = DATA_DIR + "/reference1";
            String REF2_DIR = DATA_DIR + "/reference2";
        }

        String VLSP_DIR = DATA_DIR + "/vlsp";
        String IWSLT15_DIR = DATA_DIR + "/iwslt15";
        String IWSLT15_EN = IWSLT15_DIR + "/iwslt15.test_en.snt";
        String IWSLT15_VI = IWSLT15_DIR + "/iwslt15.test_vi.snt";

    }

    public interface IBMS {

        String IBMS_DIR = WORK_DIR + "/ibm-models";

        public interface IBM4 {

            String IBM4_DIR = IBMS_DIR + "/ibm4";
            String TRAINED_FILE = IBM4_DIR + "/lex.f2e";
            String IBM4_FILE = IBM4_DIR + "/ibm4.model";
        }

        public interface LENGTH_WORD_BASED {

            String LENGTH_WORD_BASED_DIR = IBMS_DIR + "/length-word-based";

//            final double THRESHOLD = 0.9;
            String ALIGNED_DIR_09 = LENGTH_WORD_BASED_DIR + "/aligned-0.9";
            String ALIGNED_DIR_08 = LENGTH_WORD_BASED_DIR + "/aligned-0.8";
            String ALIGNED_DIR_07 = LENGTH_WORD_BASED_DIR + "/aligned-0.7";
            String ALIGNED_DIR_06 = LENGTH_WORD_BASED_DIR + "/aligned-0.6";
            String ALIGNED_DIR_05 = LENGTH_WORD_BASED_DIR + "/aligned-0.5";

            final int SEARCHING_LIMIT = 20;

        }
    }

    public interface LENGTH_BASED {

        String LENGTH_BASED_DIR = WORK_DIR + "/length-based";

        String LENGTH_BASED_DIR_099 = LENGTH_BASED_DIR + "/length-based-0.99";
        String LENGTH_BASED_DIR_09 = LENGTH_BASED_DIR + "/length-based-0.9";
        String LENGTH_BASED_DIR_08 = LENGTH_BASED_DIR + "/length-based-0.8";
        String LENGTH_BASED_DIR_07 = LENGTH_BASED_DIR + "/length-based-0.7";
        String LENGTH_BASED_DIR_06 = LENGTH_BASED_DIR + "/length-based-0.6";
        String LENGTH_BASED_DIR_05 = LENGTH_BASED_DIR + "/length-based-0.5";

        final double THRESHOLD = 0.99;

        final int SEARCHING_LIMIT = 20;

    }

    public interface IBM1 {

        String IBM1_DIR = WORK_DIR + "/ibm1";

        final int ITERATIVE = 4;

        String SRC_TRAIN_FILE = IBM1_DIR + "/all-sents.train.en";
        String TRG_TRAIN_FILE = IBM1_DIR + "/all-sents.train.vi";

        String IBM_MODEL1 = IBM1_DIR + "/ibm1.model";

    }

    public interface IBM1_BIG {

        String IBM1_BIG_DIR = WORK_DIR + "/ibm1-big";

        final int ITERATIVE = 4;

        String SRC_TRAIN_FILE = IBM1_BIG_DIR + "/all-sents.train.en";
        String TRG_TRAIN_FILE = IBM1_BIG_DIR + "/all-sents.train.vi";

        String IBM_MODEL1 = IBM1_BIG_DIR + "/ibm1-big.model";
        String VOCAB_EN = IBM1_BIG_DIR + "/vocab.en";
        String VOCAB_VI = IBM1_BIG_DIR + "/vocab.vi";

    }

    public interface LENGTH_WORD_BASED {

        String LENGTH_WORD_BASED_DIR = WORK_DIR + "/length-word-based";

        final double THRESHOLD = 0.9;

        String ALIGNED_DIR_09 = LENGTH_WORD_BASED_DIR + "/aligned-0.9";
        String ALIGNED_DIR_08 = LENGTH_WORD_BASED_DIR + "/aligned-0.8";
        String ALIGNED_DIR_07 = LENGTH_WORD_BASED_DIR + "/aligned-0.7";
        String ALIGNED_DIR_06 = LENGTH_WORD_BASED_DIR + "/aligned-0.6";
        String ALIGNED_DIR_05 = LENGTH_WORD_BASED_DIR + "/aligned-0.5";

        final int SEARCHING_LIMIT = 20;

    }

    public interface LENGTH_WORD_BASED_BIG {

        String LENGTH_WORD_BASED_BIG_DIR = WORK_DIR + "/length-word-based-big";

        final double THRESHOLD = 0.9;

        String ALIGNED_DIR_09 = LENGTH_WORD_BASED_BIG_DIR + "/aligned-0.9";
        String ALIGNED_DIR_08 = LENGTH_WORD_BASED_BIG_DIR + "/aligned-0.8";
        String ALIGNED_DIR_07 = LENGTH_WORD_BASED_BIG_DIR + "/aligned-0.7";
        String ALIGNED_DIR_06 = LENGTH_WORD_BASED_BIG_DIR + "/aligned-0.6";
        String ALIGNED_DIR_05 = LENGTH_WORD_BASED_BIG_DIR + "/aligned-0.5";

        final int SEARCHING_LIMIT = 20;

    }

    public interface EVALUATION {

        String EVALUATION_DIR = ROOT + "/evaluation";

        String LENG_BASED_DIR = EVALUATION_DIR + "/length-based";
        String LENG_WORD_DIR = EVALUATION_DIR + "/length-and-word";
        String LENG_WORD_BIG_DIR = EVALUATION_DIR + "/length-and-word-big";
        String LENG_WORD_BIG_W2V_DIR = EVALUATION_DIR + "/length-and-word-big-w2v";
        String MALIGN_DIR = EVALUATION_DIR + "/m-align-test2";
        String HUNALIGN_DIR = EVALUATION_DIR + "/hun-align-test2";
        String IBM4_DIR = EVALUATION_DIR + "/ibm4";

    }

    public interface SYSTEMS {

        public interface M_ALIGN {

            String M_ALIGN_DIR = ROOT + "/m-align";

            String TRAIN_09_DIR = M_ALIGN_DIR + "/test2-0.9";
            String TRAIN_08_DIR = M_ALIGN_DIR + "/test2-0.8";
            String TRAIN_07_DIR = M_ALIGN_DIR + "/test2-0.7";
            String TRAIN_06_DIR = M_ALIGN_DIR + "/test2-0.6";
            String TRAIN_05_DIR = M_ALIGN_DIR + "/test2-0.5";

        }

        public interface HUN_ALIGN {

            String HUN_ALIGN_DIR = ROOT + "/hun-align";

            String ALIGNED = HUN_ALIGN_DIR + "/test2.aligned";
            String ALIGNED_EN = HUN_ALIGN_DIR + "/test2.en.aligned";
            String ALIGNED_VI = HUN_ALIGN_DIR + "/test2.vi.aligned";

        }

    }

    public interface W2V {

        String W2V_DIR = ROOT + "/w2v";

        String OOV_EN = W2V_DIR + "/oov.vocab.en";
        String OOV_VI = W2V_DIR + "/oov.vocab.vi";

        String SIM_EN = W2V_DIR + "/most-similar.en";
        String SIM_VI = W2V_DIR + "/most-similar.vi";

        String NEW_MODEL1 = W2V_DIR + "/ibm1-new.model";
        String MODEL1 = W2V_DIR + "/ibm1.model";

        public interface ALIGNED {

            String ALIGNED_DIR = W2V_DIR + "/aligned";
            String ALIGNED_DIR_09 = ALIGNED_DIR + "/aligned-0.9";
            String ALIGNED_DIR_08 = ALIGNED_DIR + "/aligned-0.8";
            String ALIGNED_DIR_07 = ALIGNED_DIR + "/aligned-0.7";
            String ALIGNED_DIR_06 = ALIGNED_DIR + "/aligned-0.6";
            String ALIGNED_DIR_05 = ALIGNED_DIR + "/aligned-0.5";

        }

    }

}
