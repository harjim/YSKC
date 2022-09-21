using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class equiadd : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<DateTime>(
                name: "depreciationDate",
                table: "equipment",
                nullable: false,
                defaultValue: new DateTime(2019, 7, 16, 0, 0, 0, 0, DateTimeKind.Local));

            migrationBuilder.AddColumn<decimal>(
                name: "depreciationRate",
                table: "equipment",
                type: "decimal(10,4)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<int>(
                name: "usagePower",
                table: "equipment",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "depreciationDate",
                table: "equipment");

            migrationBuilder.DropColumn(
                name: "depreciationRate",
                table: "equipment");

            migrationBuilder.DropColumn(
                name: "usagePower",
                table: "equipment");
        }
    }
}
