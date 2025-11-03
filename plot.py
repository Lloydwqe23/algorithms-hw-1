import matplotlib.pyplot as plt
import matplotlib.ticker as ticker

sizes = []
variants = set()
data = {}

try:
    with open("results.csv", "r") as f:
        next(f)
        for line in f:
            line = line.strip()
            if not line:
                continue
            size, variant, ops, mem = line.split(",")
            size = int(size)
            ops = int(ops)
            mem = float(mem)
            sizes.append(size)
            variants.add(variant)
            data[(variant, size)] = (ops, mem)
except FileNotFoundError:
    print("Error: results.csv not found.")
    exit()
except Exception as e:
    print(f"Error reading file: {e}")
    exit()


sizes = sorted(list(set(sizes)))
variants = sorted(list(variants))

plt.figure(figsize=(10, 6))
for v in variants:
    xs = []
    ys = []
    for s in sizes:
        if (v, s) in data:
            xs.append(s)
            ys.append(data[(v, s)][0])
    if xs:
        plt.plot(xs, ys, marker='o', label=v)
        for x, y in zip(xs, ys):
            label = '{:,.0f}'.format(y)
            
            plt.annotate(
                label,
                (x, y),
                textcoords="offset points",
                xytext=(7, 7),
                ha='left',
                fontsize=8,
                color='darkslategray'
            )

plt.xscale("log")
plt.yscale("log")
ax = plt.gca() 
ax.xaxis.set_major_formatter(ticker.ScalarFormatter())
ax.xaxis.set_minor_formatter(ticker.NullFormatter())
plt.xticks(sizes) 
ax.yaxis.set_major_formatter(ticker.FuncFormatter(lambda y, _: '{:,.0f}'.format(y)))
ax.yaxis.set_minor_formatter(ticker.NullFormatter())
plt.xlabel("DB size")
plt.ylabel("Operations in 10 sec")
plt.title("Performance comparison (Operations)")
plt.grid(False)
plt.legend()
plt.tight_layout()
plt.savefig("ops_graph.png")
print("OPS graph saved as ops_graph.png")

plt.figure(figsize=(10, 6))
for v in variants:
    xs = []
    ys = []
    for s in sizes:
        if (v, s) in data:
            xs.append(s)
            ys.append(data[(v, s)][1])
    if xs:
        plt.plot(xs, ys, marker='o', label=v)
        for x, y in zip(xs, ys):
            label = '{:,.0f}'.format(y)
            
            plt.annotate(
                label,
                (x, y),
                textcoords="offset points",
                xytext=(7, 7),
                ha='left',
                fontsize=8,
                color='darkslategray'
            )

plt.xscale("log")
ax = plt.gca()
ax.xaxis.set_major_formatter(ticker.ScalarFormatter())
ax.xaxis.set_minor_formatter(ticker.NullFormatter())
plt.xticks(sizes)

ax.yaxis.set_major_formatter(ticker.FuncFormatter(lambda y, _: '{:,.1f}'.format(y)))

plt.xlabel("DB size")
plt.ylabel("Memory usage")
plt.title("Memory comparison")
plt.grid(False)
plt.legend()
plt.tight_layout()
plt.savefig("memory_graph.png")
print("Memory graph saved as memory_graph.png")

plt.figure(figsize=(10, 6))

for v in variants:
    xs = []
    ys = []
    for s in sizes:
        if (v, s) in data:
            ops, mem = data[(v, s)]
            if mem > 0:
                xs.append(s)
                ys.append(ops / mem)
    if xs:
        plt.plot(xs, ys, marker='o', label=v)
        for x, y in zip(xs, ys):
            label = '{:,.0f}'.format(y)
            
            plt.annotate(
                label,
                (x, y),
                textcoords="offset points",
                xytext=(7, 7),
                ha='left',
                fontsize=8,
                color='darkslategray'
            )

plt.xscale("log")
plt.yscale("log")

ax = plt.gca()

ax.xaxis.set_major_formatter(ticker.ScalarFormatter())
ax.xaxis.set_minor_formatter(ticker.NullFormatter())
plt.xticks(sizes)

ax.yaxis.set_major_formatter(ticker.ScalarFormatter())

plt.xlabel("DB size (Log Scale)")
plt.ylabel("Operations / Memory (Log Scale)")
plt.title("Efficiency comparison (OPS / Memory)")
plt.grid(False)
plt.legend()
plt.tight_layout()
plt.savefig("efficiency_graph.png")
print("Efficiency graph saved as efficiency_graph.png")