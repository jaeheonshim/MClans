package com.jaeheonshim.mclans;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;

import java.util.Objects;

public class DataChunk {
    private int x;
    private int z;
    private String worldUUid;

    public DataChunk() {

    }

    public DataChunk(Chunk chunk) {
        this.x = chunk.getX();
        this.z = chunk.getZ();
        this.worldUUid = chunk.getWorld().getUID().toString();
    }

    public Chunk getChunk() {
        return Bukkit.getWorld(worldUUid).getChunkAt(x, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataChunk dataChunk = (DataChunk) o;
        return x == dataChunk.x &&
                z == dataChunk.z &&
                Objects.equals(worldUUid, dataChunk.worldUUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, z, worldUUid);
    }
}
