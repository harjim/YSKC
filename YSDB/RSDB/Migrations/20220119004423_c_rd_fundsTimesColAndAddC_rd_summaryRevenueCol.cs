using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class c_rd_fundsTimesColAndAddC_rd_summaryRevenueCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "amount",
                table: "c_rd_summary",
                newName: "costAmount");

            migrationBuilder.AddColumn<decimal>(
                name: "revenue",
                table: "c_rd_summary",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<DateTime>(
                name: "createTime",
                table: "c_rd_funds",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "c_rd_funds",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "revenue",
                table: "c_rd_summary");

            migrationBuilder.DropColumn(
                name: "createTime",
                table: "c_rd_funds");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "c_rd_funds");

            migrationBuilder.RenameColumn(
                name: "costAmount",
                table: "c_rd_summary",
                newName: "amount");
        }
    }
}
