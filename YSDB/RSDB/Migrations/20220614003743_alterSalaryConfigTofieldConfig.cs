using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class alterSalaryConfigTofieldConfig : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropPrimaryKey(
                name: "PK_salaryConfig",
                table: "salaryConfig");

            migrationBuilder.RenameTable(
                name: "salaryConfig",
                newName: "fieldConfig");

            migrationBuilder.AddPrimaryKey(
                name: "PK_fieldConfig",
                table: "fieldConfig",
                column: "id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropPrimaryKey(
                name: "PK_fieldConfig",
                table: "fieldConfig");

            migrationBuilder.RenameTable(
                name: "fieldConfig",
                newName: "salaryConfig");

            migrationBuilder.AddPrimaryKey(
                name: "PK_salaryConfig",
                table: "salaryConfig",
                column: "id");
        }
    }
}
