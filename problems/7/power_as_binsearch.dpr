program power;

const
    MAX = 200000;

type
    trect = record
        x, y: int64;
        py: longint;
    end;

    tarr = array [1..MAX] of trect;

procedure sortx(var a: tarr; l, r: longint);
var
    i, j: longint;
    x, t: trect;
begin
    x := a[l + random(r - l + 1)];
    i := l;
    j := r;
    while i <= j do begin
        while (a[i].x > x.x) do 
            inc(i);
        while (a[j].x < x.x) do 
            dec(j);
        if i <= j then begin
            t := a[i]; a[i] := a[j]; a[j] := t;
            inc(i);
            dec(j);
        end;
    end;
    if l < j then
        sortx(a, l, j);
    if i < r then
        sortx(a, i, r);
end;

procedure sorty(var a: tarr; l, r: longint);
var
    i, j: longint;
    x, t: trect;
begin
    x := a[l + random(r - l + 1)];
    i := l;
    j := r;
    while i <= j do begin
        while (a[i].y > x.y) do 
            inc(i);
        while (a[j].y < x.y) do 
            dec(j);
        if i <= j then begin
            t := a[i]; a[i] := a[j]; a[j] := t;
            inc(i);
            dec(j);
        end;
    end;
    if l < j then
        sorty(a, l, j);
    if i < r then
        sorty(a, i, r);
end;

var
    s: array [1..4 * MAX] of longint;

procedure update(p, l, r, d: longint);
var
    m: longint;
begin
    if l = r then begin
        s[p] := s[p] + 1;
        exit;
    end;
    m := (l + r) div 2;
    if d <= m then
        update(2 * p, l, m, d)
    else
        update(2 * p + 1, m + 1, r, d);
    s[p] := s[2 * p] + s[2 * p + 1];
end;

function sum(p, l, r, v: longint): longint;
var
    m: longint;
begin
    if r <= v then begin
        sum := s[p];
        exit;
    end;
    if l > v then begin
        sum := 0;
        exit;
    end;
    m := (l + r) div 2;
    sum := sum(2 * p, l, m, v) + sum(2 * p + 1, m + 1, r, v);
end;

var
    n, k: longint;
    l, r, m: longint;
    p: tarr;
    i: longint;
    ys: array [1..MAX] of longint;
    y, ans: int64;

begin
    reset(input, 'power.in');
    rewrite(output, 'power.out');

    read(n, k);
    for i := 1 to n do begin
        read(p[i].x, p[i].y);
    end;

    sorty(p, 1, n);
    for i := 1 to n do begin
        p[i].py := i;
        ys[i] := p[i].y;
    end;

    sortx(p, 1, n);
    ans := 0;
    for i := 1 to n do begin
        update(1, 1, n, p[i].py);
        if i >= k then begin
            l := 0;
            r := n + 1;
            while l < r - 1 do begin
                m := (l + r) div 2;
                if sum(1, 1, n, m) >= k then
                    r := m
                else
                    l := m;
            end;
            if p[i].x * ys[r] > ans then
                ans := p[i].x * ys[r];
        end;
    end;

    writeln(ans);

    close(input);
    close(output);
end.