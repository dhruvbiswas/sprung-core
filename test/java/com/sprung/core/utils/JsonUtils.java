package com.sprung.core.utils;

import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

public class JsonUtils {

    public static List<String> generateJsonPaths(String jsonPath) {
        List<String> jsonPathsList = new ArrayList<>();
        //Add a regex check as well
        if(jsonPath != null && jsonPath.length() > 0) {
            //Split the json string into tokens
            String[] tokens = jsonPath.split("\\.");
            Stack<String> dftStack = new Stack<String>();
            List<String> tokensList = new ArrayList<>();
            for(int i = 0; i < tokens.length; i++) {
                tokensList.add(tokens[i]);
            }
            JsonUtils.generateJsonPaths(tokensList, dftStack,0, jsonPathsList);
        }
        return jsonPathsList;
    }

    /*
     * Use a Depth First Traversal to construct JSON Path
     */
    public static void generateJsonPaths(List<String> tokensList, Stack<String> dftStack, int currentTokenDFTIndex, List<String> paths) {
        if(currentTokenDFTIndex < tokensList.size()) {
            //Check if the element is an indexed element
            if(RegExUtil.IsMatch(tokensList.get(currentTokenDFTIndex), "^.+\\[.*?]$")) {
                //Run a regex group match and extract the start and end indexes
                //Construct a json sub-path using each of the indexes from start to end in a
                //for loop and insert each index into the dftstack
                //recursively call generateJsonPaths using the nextIndex and the current dftStack
                //Effectively using the nextIndex would push the stack into the next json token
                String tokenName = RegExUtil.getGroupedMatches(tokensList.get(currentTokenDFTIndex), "^(.+)\\[.*?]").get(0);
                List<Integer> indexes = JsonUtils.generateIndexes(tokensList.get(currentTokenDFTIndex));
                if(indexes != null && indexes.size() > 0) {
                    for(int i = 0; i < indexes.size(); i++) {
                        String tokenWithArrayIndex = tokenName + "[" + indexes.get(i) + "]";
                        dftStack.push(tokenWithArrayIndex);
                        JsonUtils.generateJsonPaths(tokensList, dftStack, currentTokenDFTIndex + 1, paths);
                        //Pop from dfstack once back from the recursive call
                        dftStack.pop();
                    }
                }
            } else {
                //Add the path into dftStack
                dftStack.push(tokensList.get(currentTokenDFTIndex));
                JsonUtils.generateJsonPaths(tokensList, dftStack, currentTokenDFTIndex + 1, paths);
                //Pop from dftStack once back from the recursive call
                dftStack.pop();
            }
        } else {
            //We have hit a leaf node....
            //Pop all elements from the stack and construct a dotted json path
            //And push the elements into a list
            List<String> allPopped = new ArrayList<>();
            while(!dftStack.empty()) {
                String popped = dftStack.pop();
                allPopped.add(popped);
            }
            //Constrct a json path String
            StringBuilder builder = new StringBuilder();
            builder.append(".");
            for(int i = allPopped.size() - 1; i >= 0; i--) {
                if(i == 0) {
                    builder.append(allPopped.get(i));
                } else {
                    builder.append(allPopped.get(i));
                    builder.append(".");
                }
                //push the elements back into the stack
                dftStack.push(allPopped.get(i));
            }
            String path = builder.toString();
            paths.add("$" + path);
            //Return from the recursive call
            return;
        }
    }

    public static List<Integer> generateIndexes(String token) {
        List<Integer> listOfIndexes = new ArrayList<>();
        //Check if its a range token
        if(RegExUtil.IsMatch(token, "^.+\\[(\\d)-(\\d)\\]$")) {
            //its a range token
            List<String> indexRanges = RegExUtil.getGroupedMatches(token, "^.+\\[(\\d)-(\\d)\\]");
            for(int i = Integer.parseInt(indexRanges.get(0)); i <= Integer.parseInt(indexRanges.get(1)); i++) {
                listOfIndexes.add(i);
            }
        } else {
            //the pattern should be like abcd[1,3,5,7] or abcd[1-3,4,5-8,10]
            String indexesString = RegExUtil.getGroupedMatches(token, "^.+\\[(.*?)]").get(0);
            String[] csvSeparatedIndexTokens = indexesString.split(",");
            for(int i = 0; i < csvSeparatedIndexTokens.length; i++) {
                String t = csvSeparatedIndexTokens[i];
                if(RegExUtil.IsMatch(t, "\\d-\\d")) {
                    //This is a range token
                    List<Integer> subRanges = JsonUtils.generateIndexes("a" + "[" + t + "]");
                    if(subRanges != null && subRanges.size() > 0) {
                        listOfIndexes.addAll(subRanges);
                    }
                } else {
                    listOfIndexes.add(Integer.parseInt(csvSeparatedIndexTokens[i]));
                }
            }
        }
        return listOfIndexes;
    }

    public static void main(String[] args) {
        //Given a path similar to the following with  multiple nested array elements
        //Generate all possible json paths that can be formed from the string
        String jsonPathString = "abcd[1-3,6-8].pqrs.xyz[2-4].lmn";
        //String jsonPathString = "house.room[1-2].doom[0-1].field.field_arr[0-1]";
        List<String> jsonPathsList = JsonUtils.generateJsonPaths(jsonPathString);
        jsonPathsList.stream().forEach(jsonPath -> {
            System.out.println(jsonPath);
        });
    }

}