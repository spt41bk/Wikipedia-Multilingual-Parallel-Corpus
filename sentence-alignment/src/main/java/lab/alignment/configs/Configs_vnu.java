/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.alignment.configs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static lab.alignment.configs.Configs.ROOT;

/**
 *
 * @author s1420211
 */
public interface Configs_vnu {

    String WORK_DIR = ROOT + "/data/vnu";

    public interface EVS {

        String EVS_DIR = WORK_DIR + "/evs";

        public interface LENGTH_BASED {

            String LENGTH_BASED_DIR = EVS_DIR + "/length-based";

            final double THRESHOLD = 0.99;

            final int SEARCHING_LIMIT = 20;

        }

    }

    public interface EVALUATION {

        String EVALUATION_DIR = WORK_DIR + "/evaluation";

        public interface SCORES {

            public interface M_ALIGN {

                String M_ALIGN_DIR = EVALUATION_DIR + "/m-align";

            }
        }

        public interface SYSTEMS {

            public interface HUN_ALIGN {
            }

            public interface M_ALIGN {

                String M_ALIGN_DIR = ROOT + "/methods/m-align/bilingual-sentence-aligner/work-dir";

                String TRAIN_09_DIR = M_ALIGN_DIR + "/test-0.9";
                String TRAIN_08_DIR = M_ALIGN_DIR + "/test-0.8";
                String TRAIN_07_DIR = M_ALIGN_DIR + "/test-0.7";
                String TRAIN_06_DIR = M_ALIGN_DIR + "/test-0.6";
                String TRAIN_05_DIR = M_ALIGN_DIR + "/test-0.5";

            }
        }

    }

    public interface TEST {

        String TEST_FILE_STATISTICS = WORK_DIR + "/test-data.statistics.txt";

        List<String> NAME_LIST = new ArrayList<>(Arrays.asList(
                "33138", "33139",
                "33140", "33141", "33142", "33144", "33148", "33149",
                "33150", "33151", "33152", "33154", "33155", "33157",
                "33160", "33162", "33165", "33166",
                "33180", "33181", "33182", "33183", "33184", "33185", "33186", "33187", "33188", "33189",
                "33190", "33191", "33193", "33194", "33195", "33196", "33197", "33198"
        ));

        public interface INPUT {

            String INPUT_DIR = WORK_DIR + "/vnu-data/ForTest/Test1_CorpusAK-Tach_tu";
            String TEST_DIR = INPUT_DIR + "/Run";
            String REF_EN = INPUT_DIR + "/test1AK_handalign_lwc/en";
            String REF_VI = INPUT_DIR + "/test1AK_handalign_lwc/vi";
        }

        public interface TEST_DATA {

            String TEST_DATA_DIR = WORK_DIR + "/test";
        }
        
        public interface VLSP{
            String VLSP_DIR=WORK_DIR+"/vlsp";
        }

        public interface REF_DATA {

            String REF_DATA_DIR = WORK_DIR + "/reference";
            String EN_DIR = REF_DATA_DIR + "/en";
            String VI_DIR = REF_DATA_DIR + "/vi";
        }
    }

}
