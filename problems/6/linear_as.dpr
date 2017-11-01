program linear;

const
    MAX = 200000;
    INF = 2000000001;

type
    tarr = array [1..MAX] of longint;

var
    n, m, i: longint;
    x, v, t, s: tarr;
    sp: longint;
    cur: longint;
    die: tarr;


procedure sort(var a: tarr; l, r: longint);
var
    i, j, x, t: longint;
begin
    x := a[l + random(r - l + 1)];
    i := l;
    j := r;
    while i <= j do begin
        while a[i] < x do 
            inc(i);
        while a[j] > x do
            dec(j);
        if i <= j then begin
            t := a[i]; a[i] := a[j]; a[j] := t;
            inc(i);
            dec(j);
        end;
    end;
    if l < j then
        sort(a, l, j);
    if i < r then
        sort(a, i, r);
end;

begin
    reset(input, 'linear.in');
    rewrite(output, 'linear.out');

    read(n);
    for i := 1 to n do begin
        read(x[i], v[i]);
        die[i] := INF;
    end;

    read(m);
    for i := 1 to m do begin
        read(t[i]);
        t[i] := t[i] * 2;
    end;

    sp := 0;
    for i := 1 to n do begin
        if (v[i] < 0) and (sp > 0) then begin
            die[i] := x[i] - x[s[sp]];
            die[s[sp]] := die[i];
            sp := sp - 1;
        end;
        if (v[i] > 0) then begin
            sp := sp + 1;
            s[sp] := i;
        end;
    end;

    sort(die, 1, n);

    cur := 1;
    for i := 1 to m do begin
        while (cur <= n) and (die[cur] <= t[i]) do 
            inc(cur);
        writeln(n - cur + 1);
    end;

    close(input);
    close(output);
end.