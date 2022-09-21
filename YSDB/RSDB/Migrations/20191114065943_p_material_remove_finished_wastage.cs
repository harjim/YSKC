using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class p_material_remove_finished_wastage : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "finished",
                table: "p_material");

            migrationBuilder.DropColumn(
                name: "wastage",
                table: "p_material");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<decimal>(
                name: "finished",
                table: "p_material",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "wastage",
                table: "p_material",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);
        }
    }
}
