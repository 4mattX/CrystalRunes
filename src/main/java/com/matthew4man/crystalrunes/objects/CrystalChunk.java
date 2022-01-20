package com.matthew4man.crystalrunes.objects;

import org.bukkit.Chunk;


public class CrystalChunk {

    private Chunk chunk;
    private Crystal crystal;

    public CrystalChunk(Chunk chunk, Crystal crystal) {
        this.setChunk(chunk);
        this.setCrystal(crystal);
    }

    public Chunk getChunk() {
        return chunk;
    }

    public void setChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    public Crystal getCrystal() {
        return crystal;
    }

    public void setCrystal(Crystal crystal) {
        this.crystal = crystal;
    }
}
