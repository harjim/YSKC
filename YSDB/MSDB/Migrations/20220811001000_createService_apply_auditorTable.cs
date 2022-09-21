using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createService_apply_auditorTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "finaDirectorId",
                table: "service_apply");

            migrationBuilder.DropColumn(
                name: "finaManagerId",
                table: "service_apply");

            migrationBuilder.DropColumn(
                name: "techDirectorId",
                table: "service_apply");

            migrationBuilder.DropColumn(
                name: "techManagerId",
                table: "service_apply");

            migrationBuilder.CreateTable(
                name: "service_apply_auditor",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    serviceApplyId = table.Column<int>(nullable: false),
                    techManagerId = table.Column<int>(nullable: true),
                    techDirectorId = table.Column<int>(nullable: true),
                    finaManagerId = table.Column<int>(nullable: true),
                    finaDirectorId = table.Column<int>(nullable: true),
                    instanceId = table.Column<int>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_service_apply_auditor", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_service_apply_auditor_serviceApplyId",
                table: "service_apply_auditor",
                column: "serviceApplyId",
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "service_apply_auditor");

            migrationBuilder.AddColumn<int>(
                name: "finaDirectorId",
                table: "service_apply",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "finaManagerId",
                table: "service_apply",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "techDirectorId",
                table: "service_apply",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "techManagerId",
                table: "service_apply",
                nullable: true);
        }
    }
}
