package com.company;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            walkToPaths(args[0], args[1]);
        }
        catch (IOException exc)
        {
            exc.printStackTrace();
        }
    }

    private static void walkToPaths(String inFileName, String outFileName) throws IOException
    {
        try (FileWriter writer = new FileWriter(outFileName))
        {
            for (String line : Files.lines(Paths.get(inFileName), StandardCharsets.UTF_8)
                    .collect(Collectors.toList()))
            {
                Path path = Paths.get(line);
                try (Stream<Path> paths = Files.walk(path))
                {
                    calculateHashes(paths.collect(Collectors.toList()), writer);
                }
            }
        }
    }

    private static void calculateHashes(List<Path> paths, FileWriter writer) throws IOException
    {
        for (Path path : paths)
        {
            File file = path.toFile();
            if (file.isFile())
            {
                try
                {
                    writer.write(Integer.toHexString(getHashSum(file)) + ' ' + path + '\n');
                }
                catch (IOException exc)
                {
                    writer.write("00000000 " + path + '\n');
                }
            }
        }
    }

    private static final int FNV_32_PRIME = 0x01000193;

    private static int getHashSum(File file) throws IOException
    {
        int hashSum = 0x811c9dc5;

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))
        {
            while (reader.ready())
            {
                hashSum ^= reader.read();
                hashSum *= FNV_32_PRIME;
            }
        }

        return hashSum;
    }
}