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
public interface Configs_evs_big {

    String WORK_DIR = ROOT + "/evs-big";

    public interface LENGTH_BASED {

        String LENGTH_BASED_DIR = WORK_DIR + "/length-based";
        

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
    
    public interface LENGTH_WORD_BASED {

        String LENGTH_WORD_BASED_DIR = WORK_DIR + "/length-word-based";
        

        final double THRESHOLD = 0.9;
        
        String ALIGNED_DIR_09=LENGTH_WORD_BASED_DIR+"/aligned-0.9";
        String ALIGNED_DIR_08=LENGTH_WORD_BASED_DIR+"/aligned-0.8";
        String ALIGNED_DIR_07=LENGTH_WORD_BASED_DIR+"/aligned-0.7";
        String ALIGNED_DIR_06=LENGTH_WORD_BASED_DIR+"/aligned-0.6";
        String ALIGNED_DIR_05=LENGTH_WORD_BASED_DIR+"/aligned-0.5";
        

        final int SEARCHING_LIMIT = 20;

    }

}
