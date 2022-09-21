using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class p_rdAgg_configAddMonthCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_p_rdAgg_config_companyId_type",
                table: "p_rdAgg_config");

            migrationBuilder.AddColumn<DateTime>(
                name: "month",
                table: "p_rdAgg_config",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.CreateIndex(
                name: "IX_p_rdAgg_config_companyId_type_month",
                table: "p_rdAgg_config",
                columns: new[] { "companyId", "type", "month" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_p_rdAgg_config_companyId_type_month",
                table: "p_rdAgg_config");

            migrationBuilder.DropColumn(
                name: "month",
                table: "p_rdAgg_config");

            migrationBuilder.CreateIndex(
                name: "IX_p_rdAgg_config_companyId_type",
                table: "p_rdAgg_config",
                columns: new[] { "companyId", "type" },
                unique: true);
        }
    }
}
