using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class createC_workerTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "ysFina",
                table: "p_audit_opt");

            migrationBuilder.DropColumn(
                name: "ysTech",
                table: "p_audit_opt");

            migrationBuilder.CreateTable(
                name: "c_worker",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    companyId = table.Column<int>(nullable: false),
                    year = table.Column<int>(nullable: false),
                    ysTech = table.Column<string>(maxLength: 30, nullable: true),
                    ysFina = table.Column<string>(maxLength: 30, nullable: true),
                    createTime = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_c_worker", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_c_worker_companyId_year",
                table: "c_worker",
                columns: new[] { "companyId", "year" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "c_worker");

            migrationBuilder.AddColumn<string>(
                name: "ysFina",
                table: "p_audit_opt",
                maxLength: 30,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "ysTech",
                table: "p_audit_opt",
                maxLength: 30,
                nullable: true);
        }
    }
}
