package context.eventDrivenSim;

import std.StdDraw;

import java.awt.*;

/**
 * @author wulizi
 */
public class Particle {
    private static final double INFINITY = Double.POSITIVE_INFINITY;
    /**
     * 位置
     */
    private double rx, ry;

    /**
     * 速度
     */
    private double vx, vy;

    /**
     * 质量
     */
    private double mass;

    /**
     * 半径
     */
    private double radius;

    /**
     * 碰撞次数
     */
    private int count;

    private Color color;


    public Particle(double rx, double ry, double vx,
                    double vy, double mass, double radius,
                    int count, Color color) {
        this.rx = rx;
        this.ry = ry;
        this.vx = vx;
        this.vy = vy;
        this.mass = mass;
        this.radius = radius;
        this.count = count;
        this.color = color;
    }

    public Particle() {
        rx = Math.random();
        ry = Math.random();
        vx = 0.01 * (Math.random() - 0.5);
        vy = 0.01 * (Math.random() - 0.5);
        mass = 0.5;
        radius = 0.01;
        color = Color.BLACK;
    }

    public void move(double dt) {
        rx += vx * dt;
        ry += vy * dt;
    }

    public int count() {
        return count;
    }

    public void draw() {
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(rx, ry, radius);
    }


    public double timeToHit(Particle b) {
        Particle a = this;
        if (a == b) {
            return INFINITY;
        }
        double dx  = b.rx - a.rx;
        double dy  = b.ry - a.ry;
        double dvx = b.vx - a.vx;
        double dvy = b.vy - a.vy;
        double dvdr = dx*dvx + dy*dvy;
        if (dvdr > 0) {
            return INFINITY;
        }
        double dvdv = dvx*dvx + dvy*dvy;
        double drdr = dx*dx + dy*dy;
        double sigma = a.radius + b.radius;
        double d = (dvdr*dvdr) - dvdv * (drdr - sigma*sigma);
        if (d < 0) {
            return INFINITY;
        }
        return -(dvdr + Math.sqrt(d)) / dvdv;
    }

    /**
     * 和水平墙面碰撞的时间
     */
    public double timeToHitHorizontalWall() {
        if (vy > 0) {
            return (1.0 - ry - radius) / vy;
        } else if (vy < 0) {
            return (radius - ry) / vy;
        } else {
            return INFINITY;
        }
    }

    /**
     * 和垂直墙面碰撞的时间
     */
    public double timeToHitVerticalWall() {
        if (vx > 0) {
            return (1.0 - rx - radius) / vx;
        } else if (vx < 0) {
            return (radius - rx) / vx;
        } else {
            return INFINITY;
        }
    }

    /**
     * 碰撞后的速度
     * @param that 另外一个
     */
    public void bounceOff(Particle that) {
        double dx  = that.rx - this.rx;
        double dy  = that.ry - this.ry;
        double dvx = that.vx - this.vx;
        double dvy = that.vy - this.vy;
        double dvdr = dx*dvx + dy*dvy;
        double dist = this.radius + that.radius;

        double F = 2 * this.mass * that.mass * dvdr / ((this.mass + that.mass) * dist);
        double fx = F * dx / dist;
        double fy = F * dy / dist;

        this.vx += fx / this.mass;
        this.vy += fy / this.mass;
        that.vx -= fx / that.mass;
        that.vy -= fy / that.mass;

        this.count++;
        that.count++;
    }


    /**
     * 与垂直墙面碰撞
     */
    public void bounceOffVerticalWall() {
        vx = -vx;
        count++;
    }

    /**
     * 与水平墙面碰撞
     */
    public void bounceOffHorizontalWall() {
        vy = -vy;
        count++;
    }

    public double kineticEnergy() { 
        return 0.5 * mass * (vx*vx + vy*vy); 
    }
}
