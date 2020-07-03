package context.eventDrivenSim;

import sort.priorityqueue.MinQueue;
import std.StdDraw;

/**
 * @author 伍立子
 */
public class CollisionSystem {
    /**
     * 最小优先队列
     */
    private MinQueue<Event> pq;
    /**
     * 仿真时钟事件
     */
    private double t = 0.0;
    /**
     * 每个时钟声中重绘事件的数目
     */
    private double hz = 0.5;

    /**
     * 粒子数组
     */
    private Particle[] particles;

    public CollisionSystem(Particle[] particles) {
        this.particles = particles;
    }

    /**
     * 碰撞预测
     *
     * @param a     粒子a
     * @param limit 规定时间内
     */
    private void predict(Particle a, double limit) {
        if (a == null) {
            return;
        }
        for (Particle particle : particles) {
            double dt = a.timeToHit(particle);
            if (t + dt <= limit) {
                pq.insert(new Event(t + dt, a, particle));
            }
        }

        double dtX = a.timeToHitHorizontalWall();
        if (t + dtX <= limit) {
            pq.insert(new Event(t + dtX, a, null));
        }
        double dtY = a.timeToHitVerticalWall();
        if (t + dtY <= limit) {
            pq.insert(new Event(t + dtY, null, a));
        }

    }

    /**
     * 重画
     *
     * @param limit 限制时间
     */
    private void redraw(double limit) {
        StdDraw.clear();
        for (Particle particle : particles) {
            particle.draw();
        }
        StdDraw.show(20);
        if (t < limit) {
            pq.insert(new Event(t + 1.0 / hz, null, null));
        }
    }

    /**
     * 基于事件的有限秒仿真
     *
     * @param limit 限制时间
     */
    public void simulate(double limit) {
        pq = new MinQueue<>(Event::compareTo);
        for (Particle particle : particles) {
            predict(particle, limit);
        }
        pq.insert(new Event(0, null, null));

        while (!pq.isEmpty()) {
            Event e = pq.delMin();
            if (!e.isValid()) {
                continue;
            }
            Particle a = e.a;
            Particle b = e.b;

            for (Particle particle : particles) {
                particle.move(e.time - t);
            }
            t = e.time;

            if (a != null && b != null) {
                a.bounceOff(b);
            } else if (a != null) {
                a.timeToHitVerticalWall();
            } else if (b != null) {
                b.timeToHitHorizontalWall();
            } else {
                redraw(limit);
            }

            predict(a, limit);
            predict(b, limit);
        }
    }

    /**
     * 将粒子碰撞包装成事件
     */
    private class Event implements Comparable<Event> {

        private final double time;
        private final Particle a, b;
        private final int countA, countB;

        private Event(double time, Particle a, Particle b) {
            this.time = time;
            this.a = a;
            this.b = b;

            if (a != null) {
                countA = a.count();
            } else {
                countA = -1;
            }

            if (b != null) {
                countB = b.count();
            } else {
                countB = -1;
            }
        }

        @Override
        public int compareTo(Event that) {
            return Double.compare(this.time, that.time);
        }

        public boolean isValid() {
            if (a != null && countA != a.count()) {
                return false;
            }
            if (b != null && countB != b.count()) {
                return false;
            }
            return true;
        }
    }

    public static void main(String[] args) {
        StdDraw.setXscale(1.0 / 22.0, 21.0 / 22.0);
        StdDraw.setYscale(1.0 / 22.0, 21.0 / 22.0);

        // turn on animation mode
        StdDraw.show(0);

        // the array of particles
        Particle[] particles;

        // create N random particles
        int N = 5;
        particles = new Particle[N];
        for (int i = 0; i < N; i++) {
            particles[i] = new Particle();
        }
        CollisionSystem collisionSystem = new CollisionSystem(particles);
        collisionSystem.simulate(10000);

    }
}
