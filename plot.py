import matplotlib.pyplot as plt
import matplotlib.ticker as ticker  # Імпортуємо ticker

sizes = []
variants = set()
data = {}  # data[(variant,size)] = (ops,memory)

try:
    with open("results.csv", "r") as f:
        next(f)  # skip header
        for line in f:
            line = line.strip()
            if not line:  # skip empty lines
                continue
            size, variant, ops, mem = line.split(",")
            size = int(size)
            ops = int(ops)
            # 
            # --- ЗМІНА 1: Читаємо 'mem' як float, а не int ---
            #
            mem = float(mem) # <--- ОСЬ ЗМІНА (було int)
            #
            #
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

# --- OPS graph ---
# (Тут нічого не змінилося)
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

plt.xscale("log")
plt.yscale("log")
ax = plt.gca() 
ax.xaxis.set_major_formatter(ticker.ScalarFormatter())
ax.xaxis.set_minor_formatter(ticker.NullFormatter())
plt.xticks(sizes) 
ax.yaxis.set_major_formatter(ticker.FuncFormatter(lambda y, _: '{:,.0f}'.format(y)))
ax.yaxis.set_minor_formatter(ticker.NullFormatter())
plt.xlabel("DB size")
plt.ylabel("OPS in 10 sec")
plt.title("Performance comparison (OPS)")
plt.grid(True, which="both", ls="--")
plt.legend()
plt.tight_layout()
plt.savefig("ops_graph.png")
print("OPS graph saved as ops_graph.png")


# --- MEMORY graph ---
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

plt.xscale("log")
# Y scale тут лінійна
ax = plt.gca()
ax.xaxis.set_major_formatter(ticker.ScalarFormatter())
ax.xaxis.set_minor_formatter(ticker.NullFormatter())
plt.xticks(sizes)

#
# --- ЗМІНА 2: Оновлюємо форматер осі Y, щоб показувати 1 десятковий знак ---
#
ax.yaxis.set_major_formatter(ticker.FuncFormatter(lambda y, _: '{:,.1f}'.format(y))) # <--- ЗМІНА (було .0f)
#
#

plt.xlabel("DB size")
plt.ylabel("Memory usage (KB)")
plt.title("Memory comparison")
plt.grid(True, which="both", ls="--")
plt.legend()
plt.tight_layout()
plt.savefig("memory_graph.png")
print("Memory graph saved as memory_graph.png")